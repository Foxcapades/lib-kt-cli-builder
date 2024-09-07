package io.foxcapades.lib.cli.builder.flag

import io.foxcapades.lib.cli.builder.util.properties.MutableProperty

class BooleanFlagOptions : FlagOptions<Boolean>(Boolean::class) {
  var isToggleFlag by MutableProperty<Boolean>()
}
