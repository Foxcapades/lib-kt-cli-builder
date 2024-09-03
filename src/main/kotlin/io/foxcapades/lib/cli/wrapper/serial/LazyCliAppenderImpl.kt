package io.foxcapades.lib.cli.wrapper.serial

import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.ResolvedArgument
import io.foxcapades.lib.cli.wrapper.ResolvedComponent
import io.foxcapades.lib.cli.wrapper.ResolvedFlag
import io.foxcapades.lib.cli.wrapper.util.BUG

/**
 * @param T Top level CLI command config class containing components to be
 * serialized.
 */
internal class LazyCliAppenderImpl<T : Any>(
  private val components: Iterator<ResolvedComponent<T, Any?>>,
  override val config: CliSerializationConfig,
  initSize: Int = 2048
) : CliAppender, Iterator<CliSegment> {
  private val queue = ArrayDeque<CliSegment>(2)

  private val buffer by lazy { StringBuilder(initSize) }

  private val appender by lazy { CliArgumentAppenderImpl(config, buffer) }

  override fun putLongFlag(name: String, hasValue: Boolean) =
    also { queue.add(
      CliSegmentImpl(
      name,
      if (hasValue)
        CliSegment.Type.LongFlagWithValue
      else
        CliSegment.Type.LongFlagNoValue
    )
    ) }

  override fun putShortFlag(name: Char, hasValue: Boolean) =
    also { queue.add(
      CliSegmentImpl(
      name.toString(),
      if (hasValue)
        CliSegment.Type.ShortFlagWithValue
      else
        CliSegment.Type.ShortFlagNoValue
    )
    ) }

  override fun putArgument(argument: Argument<*>) =
    also { argument.writeToString(appender) }

  override fun hasNext() = prepNext()

  override fun next() = queue.removeFirst()

  private fun prepNext(): Boolean {
    while (queue.isEmpty()) {
      if (!components.hasNext())
        return false

      when (val next = components.next()) {
        is ResolvedFlag     -> next.writeToString(this)
        is ResolvedArgument -> putArgument(next)
        else                -> BUG()
      }
    }

    return true
  }
}
