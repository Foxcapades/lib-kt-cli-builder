package io.foxcapades.lib.cli.wrapper.serial

enum class FlagForm {
  Short,
  Long,
  ;

  val isShort get() = this == Short

  val isLong get() = this == Long
}