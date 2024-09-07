package io.foxcapades.lib.cli.wrapper.serial

import io.foxcapades.lib.cli.wrapper.serial.CliSegment.Type.*

internal inline val CliSegment.Type.isLongFlag
  get() = this == LongFlagWithValue || this == LongFlagNoValue

internal inline val CliSegment.Type.hasValue
  get() = this == LongFlagWithValue || this == ShortFlagWithValue

internal inline val CliSegment.isLongFlag
  get() = this.type.isLongFlag

internal inline val CliSegment.hasValue
  get() = this.type.hasValue
