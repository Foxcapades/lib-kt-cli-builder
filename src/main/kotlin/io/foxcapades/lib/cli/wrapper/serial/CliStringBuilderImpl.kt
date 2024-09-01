package io.foxcapades.lib.cli.wrapper.serial

internal class CliStringBuilderImpl(
  override val config: CliSerializationConfig,
  initSize: Int = 2048,
) : CliStringBuilder {
  private val buffer = StringBuilder(initSize)

  override fun append(rawString: String) = apply { buffer.append(rawString) }

  override fun append(rawChar: Char) = apply { buffer.append(rawChar) }

  override fun appendLongFlag(name: String, hasValue: Boolean): CliStringBuilderImpl {
    buffer.append(' ').append(config.longFlagPrefix).append(name)

    if (hasValue)
      buffer.append(config.longFlagValueSeparator)

    return this
  }

  override fun appendShortFlag(name: Byte, hasValue: Boolean): CliStringBuilderImpl {
    buffer.append(' ').append(config.shortFlagPrefix).append(name)

    if (hasValue)
      buffer.append(config.shortFlagValueSeparator)

    return this
  }

  override fun toString() = buffer.toString()

  override fun clear() = apply { buffer.clear() }
}