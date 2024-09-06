package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.*
import io.foxcapades.lib.cli.wrapper.arg.BooleanArgument
import io.foxcapades.lib.cli.wrapper.arg.BooleanArgumentImpl
import io.foxcapades.lib.cli.wrapper.serial.CliAppender
import io.foxcapades.lib.cli.wrapper.serial.values.FlagPredicate
import io.foxcapades.lib.cli.wrapper.util.*

interface BooleanFlag : ScalarFlag<BooleanArgument, Boolean> {
  val isToggleFlag: Boolean
}

class BooleanFlagOptions : FlagOptions<Boolean>(Boolean::class) {
  var isToggleFlag: Boolean by SimpleProperty()
}

@Suppress("NOTHING_TO_INLINE")
inline fun booleanFlag(longForm: String, noinline action: BooleanFlagOptions.() -> Unit = {}) =
  booleanFlag { this.longForm = longForm; action() }

@Suppress("NOTHING_TO_INLINE")
inline fun booleanFlag(shortForm: Char, noinline action: BooleanFlagOptions.() -> Unit = {}) =
  booleanFlag { this.shortForm = shortForm; action() }

fun booleanFlag(action: BooleanFlagOptions.() -> Unit): BooleanFlag {
  val flag = BooleanFlagOptions().also(action)

  return BooleanFlagImpl(
    longForm   = BooleanFlagOptions::longForm.property(flag),
    shortForm  = BooleanFlagOptions::shortForm.property(flag),
    isRequired = BooleanFlagOptions::required.property(flag),
    filter     = BooleanFlagOptions::flagFilter.property(flag),
    isToggle   = BooleanFlagOptions::isToggleFlag.property<Boolean>(flag).getOr(false),
    argument   = BooleanArgumentImpl(
      default     = ArgOptions<Boolean>::default.property(flag.argument),
      isRequired  = ArgOptions<Boolean>::required.property(flag.argument),
      shouldQuote = ArgOptions<Boolean>::shouldQuote.property(flag.argument),
      formatter   = ArgOptions<Boolean>::formatter.property(flag.argument),
      filter      = ArgOptions<Boolean>::filter.property(flag.argument),
    )
  )
}

fun nullableBooleanFlag(action: NullableFlagOptions<Boolean>.() -> Unit): Flag<Argument<Boolean?>, Boolean?> =
  GeneralFlagImpl.of(NullableFlagOptions(Boolean::class).also(action))

internal class BooleanFlagImpl(
  longForm:   Property<String>,
  shortForm:  Property<Char>,
  isRequired: Property<Boolean>,
  filter:     Property<FlagPredicate<BooleanFlag, BooleanArgument, Boolean>>,
  isToggle:   Boolean,
  argument:   BooleanArgument
)
  : AbstractFlagImpl<BooleanFlag, BooleanArgument, Boolean>(longForm, shortForm, isRequired, filter, argument)
  , BooleanFlag
{
  override val isToggleFlag: Boolean = isToggle

  override fun writeToString(builder: CliAppender<*, Boolean>) {
    if (!isToggleFlag)
      return super.writeToString(builder)

    if (argument.isSet)
      builder.putPreferredFlagForm(this, false)
  }
}
