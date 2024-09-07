package io.foxcapades.lib.cli.builder.flag

import io.foxcapades.lib.cli.builder.arg.BooleanArgument

interface BooleanFlag : ScalarFlag<BooleanArgument, Boolean> {
  val isToggleFlag: Boolean
}
