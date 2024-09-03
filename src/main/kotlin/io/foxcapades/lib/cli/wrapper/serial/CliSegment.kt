package io.foxcapades.lib.cli.wrapper.serial

interface CliSegment {
  val value: String
  val type: Type

  enum class Type {
    LongFlagWithValue,
    LongFlagNoValue,
    ShortFlagWithValue,
    ShortFlagNoValue,
    QuotableValue,
    SimpleValue,
    ;
  }
}