package io.foxcapades.lib.cli.wrapper.serial

import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.ResolvedArgument
import io.foxcapades.lib.cli.wrapper.ResolvedComponent
import io.foxcapades.lib.cli.wrapper.ResolvedFlag
import io.foxcapades.lib.cli.wrapper.util.BUG
import io.foxcapades.lib.cli.wrapper.util.dump

/**
 * @param T Top level CLI command config class containing components to be
 * serialized.
 *
 * TODO: this should not directly implement [CliAppender], it should have an
 *       internal instance that it passes to components.
 */
internal class StringCliAppenderImpl<T : Any>(
  command: Array<String>,
  private val components: Iterator<ResolvedComponent<T, Any?>>,
  override val config: CliSerializationConfig,
  initSize: Int = 2048,
) : CliAppender<T, Any?> {
  private val buffer = StringBuilder(initSize)
    .apply {
      append(command[0])
      for (i in 1 ..< command.size)
        append(' ').append(command[i])
    }

  private val appender by lazy { CliArgumentAppenderImpl(config, buffer) }

  private var lastType = CliSegment.Type.SimpleValue

  override lateinit var reference: ResolvedComponent<T, Any?>
    private set

  override fun putLongFlag(name: String, hasValue: Boolean) =
    also {
      appendEmptyIfNeeded()
      buffer.append(' ').append(config.longFlagPrefix).append(name)
      if (hasValue) {
        buffer.append(config.longFlagValueSeparator)
        lastType = CliSegment.Type.LongFlagWithValue
      } else {
        lastType = CliSegment.Type.LongFlagNoValue
      }
    }

  override fun putShortFlag(name: Char, hasValue: Boolean) =
    also {
      appendEmptyIfNeeded()
      buffer.append(' ').append(config.shortFlagPrefix).append(name)
      if (hasValue) {
        buffer.append(config.shortFlagValueSeparator)
        lastType = CliSegment.Type.ShortFlagWithValue
      } else {
        lastType = CliSegment.Type.ShortFlagNoValue
      }
    }

  override fun putArgument(argument: Argument<*>) =
    also {
      if (argument.shouldQuote) {
        config.targetShell.startString(buffer::append)
        appender.forString = true
        argument.writeToString(appender)
        config.targetShell.endString(buffer::append)
        lastType = CliSegment.Type.QuotableValue
      } else {
        appender.forString = false
        argument.writeToString(appender)
        lastType = CliSegment.Type.SimpleValue
      }
    }

  override fun toString(): String {
    for (component in components) {
      reference = component

      when (component) {
        is ResolvedFlag     -> component.writeToString(this)
        is ResolvedArgument -> putArgument(component)
        else                -> BUG()
      }
    }

    appendEmptyIfNeeded()

    return buffer.dump()
  }

  private fun appendEmptyIfNeeded() {
    if (lastType.hasValue) {
      config.targetShell.startString(buffer::append)
      config.targetShell.endString(buffer::append)
    }
  }
}
