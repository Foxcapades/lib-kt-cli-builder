package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.Flag
import io.foxcapades.lib.cli.wrapper.FlagOptions
import io.foxcapades.lib.cli.wrapper.NullableFlagOptions
import io.foxcapades.lib.cli.wrapper.arg.BooleanArgumentImpl
import io.foxcapades.lib.cli.wrapper.arg.GeneralArgumentImpl
import io.foxcapades.lib.cli.wrapper.util.SimpleProperty
import io.foxcapades.lib.cli.wrapper.util.getOr
import io.foxcapades.lib.cli.wrapper.util.property

class BooleanFlagOptions : FlagOptions<Boolean>(Boolean::class) {
  var isToggleFlag: Boolean by SimpleProperty()
}

fun booleanFlag(action: FlagOptions<Boolean>.() -> Unit): BooleanFlag {
  val flag = BooleanFlagOptions().also(action)

  return BooleanFlagImpl(
    longForm   = BooleanFlagOptions::longForm.property(flag),
    shortForm  = BooleanFlagOptions::shortForm.property(flag),
    isRequired = BooleanFlagOptions::requireFlag.property(flag),
    isToggle   = BooleanFlagOptions::isToggleFlag.property<Boolean>(flag).getOr(false),
    argument   = BooleanArgumentImpl(
      default     = BooleanFlagOptions::default.property(flag),
      shouldQuote = BooleanFlagOptions::shouldQuote.property(flag),
      formatter   = BooleanFlagOptions::formatter.property(flag),
    )
  )
}

fun nullableBooleanFlag(action: NullableFlagOptions<Boolean>.() -> Unit): Flag<Argument<Boolean?>, Boolean?> {
  val flag = NullableFlagOptions(Boolean::class).also(action)

  return GeneralFlagImpl(
    longForm   = NullableFlagOptions<Boolean>::longForm.property(flag),
    shortForm  = NullableFlagOptions<Boolean>::shortForm.property(flag),
    isRequired = NullableFlagOptions<Boolean>::requireFlag.property(flag),
    argument   = GeneralArgumentImpl(
      Boolean::class,
      true,
      default     = NullableFlagOptions<Boolean>::default.property(flag),
      shouldQuote = NullableFlagOptions<Boolean>::shouldQuote.property(flag),
      isRequired  = NullableFlagOptions<Boolean>::requireArg.property(flag),
      formatter   = NullableFlagOptions<Boolean>::formatter.property(flag),
    )
  )
}
