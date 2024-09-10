package io.foxcapades.lib.cli.builder.serial.impl

import io.foxcapades.lib.cli.builder.arg.ResolvedArgumentOld
import io.foxcapades.lib.cli.builder.arg.ref.ResolvedArgument
import io.foxcapades.lib.cli.builder.component.ResolvedComponent
import io.foxcapades.lib.cli.builder.component.ResolvedComponentOld
import io.foxcapades.lib.cli.builder.flag.ResolvedFlagOld
import io.foxcapades.lib.cli.builder.flag.ref.ResolvedFlag
import io.foxcapades.lib.cli.builder.serial.CliArgumentWriter
import io.foxcapades.lib.cli.builder.serial.CliFlagWriter
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.BUG
import io.foxcapades.lib.cli.builder.util.dump
import io.foxcapades.lib.cli.builder.util.impl.StringBuilderAppender

/**
 * @param T Top level CLI command config class containing components to be
 * serialized.
 */
internal class StringCliAppenderImpl<T : Any>(
  command: Array<String>,
  private val components: Iterator<ResolvedComponentOld<T, Any?>>,
  override val config: CliSerializationConfig,
  initSize: Int = 2048,
)
  : AbstractCliAppender<T>()
  , CliFlagWriter<T, Any?>
  , CliArgumentWriter<T, Any?>
{
  private val buffer = StringBuilder(initSize)
    .apply {
      append(command[0])
      for (i in 1 ..< command.size)
        append(' ').append(command[i])
    }

  private val appender = StringBuilderAppender(buffer)

  private enum class Type { Long, Short, InQuotes, Arg, Raw }

  private lateinit var reference: ResolvedComponent

  private var last = Type.Raw

  @Suppress("UNCHECKED_CAST")
  override val flag
    get() = reference as ResolvedFlag<Any?>

  @Suppress("UNCHECKED_CAST")
  override val argument
    get() = reference as ResolvedArgument<Any?>


  override fun writeLongForm(custom: String): CliArgumentWriter<T, Any?> {
    prepNext()
    buffer.append(config.longFlagPrefix).append(custom)
    last = Type.Long

    return this
  }

  override fun writeShortForm(custom: Char): CliArgumentWriter<T, Any?> {
    prepNext()
    buffer.append(config.shortFlagPrefix).append(custom)
    last = Type.Short

    return this
  }

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
      Type.Long -> buffer.append(config.longFlagValueSeparator)
      Type.Short -> buffer.append(config.shortFlagPrefix)
      Type.Raw -> {}

      Type.InQuotes, Type.Arg -> return
    }

    if (argument.shouldQuote) {
      config.targetShell.startString(appender)
      last = Type.InQuotes
    } else {
      last = Type.Arg
    }
  }

  private fun internalWriteChar(char: Char) {
    if (config.targetShell.isArgumentSafe(char, last == Type.InQuotes))
      config.targetShell.escapeArgumentChar(char, last == Type.InQuotes, appender)
    else
      buffer.append(char)
  }

  override fun toString(): String {
    val foo: Iterable<String> = Array<String>(0) { "" }

    for (component in components) {
      reference = component

      when (component) {
        is ResolvedFlagOld -> component.writeToString(this)
        is ResolvedArgumentOld -> component.writeToString(this)
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
      config.targetShell.endString(appender)

    last = Type.Raw
  }
}
