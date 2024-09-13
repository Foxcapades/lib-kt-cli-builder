package io.foxcapades.lib.cli.builder.flag

import io.foxcapades.lib.cli.builder.util.properties.MutableDelegateProperty

class BooleanFlagOptions : FlagOptions<Boolean>(Boolean::class) {
  var isToggleFlag by MutableDelegateProperty<Boolean>()
}
