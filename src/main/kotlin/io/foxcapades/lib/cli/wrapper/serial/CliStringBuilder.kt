package io.foxcapades.lib.cli.wrapper.serial

interface CliStringBuilder {
  val config: CliSerializationConfig

  fun append(rawString: String): CliStringBuilder

  fun append(rawChar: Char): CliStringBuilder

  fun appendLongFlag(name: String, hasValue: Boolean): CliStringBuilder

  fun appendShortFlag(name: Byte, hasValue: Boolean): CliStringBuilder

  override fun toString(): String

  fun clear(): CliStringBuilder
}
