package io.foxcapades.lib.cli.wrapper.serial

import io.foxcapades.lib.cli.wrapper.serial.CliSegment.Type.*

internal inline val CliSegment.Type.isLongFlag
  get() = this == LongFlagWithValue || this == LongFlagNoValue

internal inline val CliSegment.Type.hasValue
  get() = this == LongFlagWithValue || this == ShortFlagWithValue

internal inline val CliSegment.isFlag
  get() = this.type.ordinal < 4

internal inline val CliSegment.isValue
  get() = this.type.ordinal > 3

internal inline val CliSegment.isLongFlag
  get() = this.type.isLongFlag

internal inline val CliSegment.isShortFlag
  get() = this.type == ShortFlagWithValue || this.type == ShortFlagNoValue

internal inline val CliSegment.hasValue
  get() = this.type.hasValue
