package io.foxcapades.lib.cli.wrapper.serial

import io.foxcapades.lib.cli.wrapper.ResolvedArgument
import io.foxcapades.lib.cli.wrapper.ResolvedComponent
import io.foxcapades.lib.cli.wrapper.ResolvedFlag
import io.foxcapades.lib.cli.wrapper.util.BUG
import io.foxcapades.lib.cli.wrapper.util.dump

/**
 * @param T Top level CLI command config class containing components to be
 * serialized.
 */
internal class StringCliAppenderImpl<T : Any>(
  command: Array<String>,
  private val components: Iterator<ResolvedComponent<T, Any?>>,
  override val config: CliSerializationConfig,
  initSize: Int = 2048,
) : CliFlagWriter<T, Any?>, CliArgumentWriter<T, Any?> {
  private val buffer = StringBuilder(initSize)
    .apply {
      append(command[0])
      for (i in 1 ..< command.size)
        append(' ').append(command[i])
    }

  private enum class Type { Long, Short, InQuotes, Arg, Raw }

  private var last = Type.Raw

  private inline val flag
    get() = reference as ResolvedFlag<T, Any?>

  private inline val argument
    get() = when (val ref = reference) {
      is ResolvedFlag     -> ref.argument
      is ResolvedArgument -> ref
      else                -> BUG()
    }

  override lateinit var reference: ResolvedComponent<T, Any?>
    private set

  override fun writePreferredForm(): CliArgumentWriter<T, Any?> =
    if (flag.hasLongForm) {
      if (config.preferredFlagForm.isLong || !flag.hasShortForm)
        writeLongForm()
      else
        writeShortForm()
    } else if (flag.hasShortForm) {
      writeShortForm()
    } else {
      writeLongForm(config.propertyNameFormatter.format(reference.name, config))
    }

  override fun writePreferredForm(action: CliArgumentWriter<T, Any?>.() -> Unit) =
    writePreferredForm().action()

  override fun writeLongForm(): CliArgumentWriter<T, Any?> =
    writeLongForm(flag.longForm)

  override fun writeLongForm(action: CliArgumentWriter<T, Any?>.() -> Unit) =
    writeLongForm().action()

  override fun writeLongForm(custom: String): CliArgumentWriter<T, Any?> {
    prepNext()
    buffer.append(config.longFlagPrefix).append(custom)
    last = Type.Long

    return this
  }

  override fun writeLongForm(custom: String, action: CliArgumentWriter<T, Any?>.() -> Unit) =
    writeLongForm(custom).action()

  override fun writeShortForm(): CliArgumentWriter<T, Any?> =
    writeShortForm(flag.shortForm)

  override fun writeShortForm(action: CliArgumentWriter<T, Any?>.() -> Unit) =
    writeShortForm().action()

  override fun writeShortForm(custom: Char): CliArgumentWriter<T, Any?> {
    prepNext()
    buffer.append(config.shortFlagPrefix).append(custom)
    last = Type.Short

    return this
  }

  override fun writeShortForm(custom: Char, action: CliArgumentWriter<T, Any?>.() -> Unit) =
    writeShortForm(custom).action()

  override fun rawWriteChar(char: Byte) =
    rawWriteChar(char.toInt().toChar())

  override fun rawWriteChar(char: Char) {
    prepNext()
    internalWriteChar(char)
    flush()
  }

  override fun rawWriteString(string: CharSequence) {
    prepNext()
    string.forEach(::internalWriteChar)
    flush()
  }

  override fun writeChar(char: Byte) {
    writeChar(char.toInt().toChar())
  }

  override fun writeChar(char: Char) {
    maybeStartArg()
    internalWriteChar(char)
  }

  override fun writeString(string: CharSequence) {
    maybeStartArg()
    string.forEach(::internalWriteChar)
  }

  private fun maybeStartArg() {
    when (last) {
      Type.Long     -> buffer.append(config.longFlagValueSeparator)
      Type.Short    -> buffer.append(config.shortFlagPrefix)
      Type.Raw      -> {}

      Type.InQuotes, Type.Arg -> return
    }

    if (argument.shouldQuote) {
      config.targetShell.startString(buffer::append)
      last = Type.InQuotes
    } else {
      last = Type.Arg
    }
  }

  private fun internalWriteChar(char: Char) {
    if (config.targetShell.isArgumentSafe(char, last == Type.InQuotes))
      config.targetShell.escapeArgumentChar(char, last == Type.InQuotes, buffer::append)
    else
      buffer.append(char)
  }

  override fun toString(): String {
    for (component in components) {
      reference = component

      when (component) {
        is ResolvedFlag     -> component.writeToString(this)
        is ResolvedArgument -> component.writeToString(this)
        else                -> BUG()
      }
    }

    flush()

    return buffer.dump()
  }

  private fun prepNext() {
    flush()
    buffer.append(' ')
  }

  private fun flush() {
    if (last == Type.InQuotes)
      config.targetShell.endString(buffer::append)

    last = Type.Raw
  }
}
