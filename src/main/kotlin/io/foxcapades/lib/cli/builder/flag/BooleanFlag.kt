package io.foxcapades.lib.cli.builder.flag

interface BooleanFlag : Flag<Boolean> {
  val isToggleFlag: Boolean
}

