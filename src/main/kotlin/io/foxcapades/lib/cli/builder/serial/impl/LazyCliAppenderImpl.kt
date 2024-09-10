package io.foxcapades.lib.cli.builder.serial.impl

import io.foxcapades.lib.cli.builder.arg.ref.ResolvedArgument
import io.foxcapades.lib.cli.builder.component.ResolvedComponent
import io.foxcapades.lib.cli.builder.flag.ref.ResolvedFlag
import io.foxcapades.lib.cli.builder.serial.CliArgumentWriter
import io.foxcapades.lib.cli.builder.serial.CliFlagWriter
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.BUG
import io.foxcapades.lib.cli.builder.util.dump

/**
 * @param T Top level CLI command config class containing components to be
 * serialized.
 */
internal class LazyCliAppenderImpl<T : Any>(
  components: Iterator<Any>,
  config:     CliSerializationConfig,
  initSize:   Int = 2048
)
  : AbstractCliAppender<T>(config)
  , CliFlagWriter<T, Any?>
  , CliArgumentWriter<T, Any?>
  , Iterator<String>
{
  private val components = components

  private val queue = ArrayDeque<String>(4)

  private var dirty = false

  private val buffer by lazy { StringBuilder(initSize) }

  private lateinit var reference: ResolvedComponent

  @Suppress("UNCHECKED_CAST")
  override val flag
    get() = reference as ResolvedFlag<Any?>

  @Suppress("UNCHECKED_CAST")
  override val argument
    get() = reference as ResolvedArgument<Any?>

  // region Flag Names

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

  // endregion Flag Names

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

      when ()


      reference = next

      when (next) {
        is ResolvedFlag<*> -> next.writeToString(this)
        is ResolvedArgument<*> -> next.writeToString(this)
        else                -> BUG()
      }

      dumpToQueue()
    }

    return true
  }
}
