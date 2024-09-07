package io.foxcapades.lib.cli.builder.serial

import io.foxcapades.lib.cli.builder.component.ResolvedComponent
import io.foxcapades.lib.cli.builder.arg.ResolvedArgument
import io.foxcapades.lib.cli.builder.flag.ResolvedFlag
import io.foxcapades.lib.cli.builder.util.BUG
import io.foxcapades.lib.cli.builder.util.dump

/**
 * @param T Top level CLI command config class containing components to be
 * serialized.
 */
internal class LazyCliAppenderImpl<T : Any>(
  private val components: Iterator<ResolvedComponent<T, Any?>>,
  override val config: CliSerializationConfig,
  initSize: Int = 2048
) : CliFlagWriter<T, Any?>, CliArgumentWriter<T, Any?>, Iterator<String> {
  private val queue = ArrayDeque<String>(4)

  private var dirty = false

  private val buffer by lazy { StringBuilder(initSize) }

  private inline val flag
    get() = reference as ResolvedFlag<T, Any?>

  private inline val argument
    get() = reference as ResolvedArgument<T, Any?>

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
    dumpToQueue()
    buffer.append(config.longFlagPrefix).append(custom)
    dirty = true

    if (config.longFlagValueSeparator.isNotEmpty()) {
      // If the configured long-flag value separator is whitespace, chop it.
      if (config.longFlagValueSeparator.isBlank())
        dumpToQueue()
      else
        buffer.append(config.longFlagValueSeparator)
    }

    return this
  }

  override fun writeLongForm(custom: String, action: CliArgumentWriter<T, Any?>.() -> Unit) =
    writeLongForm(custom).action()

  override fun writeShortForm(): CliArgumentWriter<T, Any?> =
    writeShortForm(flag.shortForm)

  override fun writeShortForm(action: CliArgumentWriter<T, Any?>.() -> Unit) =
    writeShortForm().action()

  override fun writeShortForm(custom: Char): CliArgumentWriter<T, Any?> {
    dumpToQueue()
    buffer.append(config.shortFlagPrefix).append(custom)
    dirty = true

    if (config.shortFlagValueSeparator.isNotEmpty()) {
      // If the configured long-flag value separator is whitespace, chop it.
      if (config.shortFlagValueSeparator.isBlank())
        dumpToQueue()
      else
        buffer.append(config.shortFlagValueSeparator)
    }

    return this
  }

  override fun writeShortForm(custom: Char, action: CliArgumentWriter<T, Any?>.() -> Unit) =
    writeShortForm(custom).action()

  override fun rawWriteChar(char: Byte) {
    dumpToQueue()
    buffer.append(char.toInt().toChar())
    dirty = true
    dumpToQueue()
  }

  override fun rawWriteChar(char: Char) {
    dumpToQueue()
    buffer.append(char)
    dirty = true
    dumpToQueue()
  }

  override fun rawWriteString(string: CharSequence) {
    dumpToQueue()
    buffer.append(string)
    dirty = true
    dumpToQueue()
  }

  override fun writeChar(char: Byte) {
    buffer.append(char.toInt().toChar())
    dirty = true
  }

  override fun writeChar(char: Char) {
    buffer.append(char)
    dirty = true
  }

  override fun writeString(string: CharSequence) {
    buffer.append(string)
    dirty = true
  }

  override fun hasNext() = prepNext()

  override fun next() = queue.removeFirst()

  private fun dumpToQueue() {
    if (dirty) {
      queue.add(buffer.dump())
      dirty = false
    }
  }

  private fun prepNext(): Boolean {
    while (queue.isEmpty()) {
      if (!components.hasNext())
        return false

      val next = components.next()
      reference = next

      when (next) {
        is ResolvedFlag -> next.writeToString(this)
        is ResolvedArgument -> next.writeToString(this)
        else                -> BUG()
      }

      dumpToQueue()
    }

    return true
  }
}
