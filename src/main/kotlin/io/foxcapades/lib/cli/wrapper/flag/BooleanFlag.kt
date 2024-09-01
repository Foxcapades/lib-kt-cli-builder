package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.BooleanArgument

interface BooleanFlag : ScalarFlag<BooleanArgument, Boolean> {
  val isToggleFlag: Boolean
}