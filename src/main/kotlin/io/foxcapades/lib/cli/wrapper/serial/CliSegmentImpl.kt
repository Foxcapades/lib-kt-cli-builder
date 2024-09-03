package io.foxcapades.lib.cli.wrapper.serial

internal data class CliSegmentImpl(
  override val value: String,
  override val type: CliSegment.Type,
) : CliSegment
