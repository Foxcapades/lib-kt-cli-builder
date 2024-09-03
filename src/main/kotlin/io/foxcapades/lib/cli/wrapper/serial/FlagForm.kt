package io.foxcapades.lib.cli.wrapper.serial

enum class FlagForm {
  Short,
  Long,
  ;

  inline val isShort get() = this == Short

  inline val isLong get() = this == Long
}