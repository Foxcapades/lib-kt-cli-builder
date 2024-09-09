package io.foxcapades.lib.cli.builder.flag

import io.foxcapades.lib.cli.builder.arg.Argument

interface BooleanFlag : Flag<Argument<Boolean>, Boolean> {
  val isToggleFlag: Boolean
}
