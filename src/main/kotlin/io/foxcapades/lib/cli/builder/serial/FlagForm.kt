package io.foxcapades.lib.cli.builder.serial

enum class FlagForm {
  Short,
  Long,
  ;

  inline val isShort get() = this == Short

  inline val isLong get() = this == Long
}
