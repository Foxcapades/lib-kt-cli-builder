package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.Flag
import io.foxcapades.lib.cli.wrapper.FlagOptions
import io.foxcapades.lib.cli.wrapper.NullableFlagOptions
import io.foxcapades.lib.cli.wrapper.arg.BooleanArgument
import io.foxcapades.lib.cli.wrapper.arg.BooleanArgumentImpl
import io.foxcapades.lib.cli.wrapper.arg.GeneralArgumentImpl
import io.foxcapades.lib.cli.wrapper.serial.CliAppender
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
    isRequired = BooleanFlagOptions::requireFlag.property(flag),
    isToggle   = BooleanFlagOptions::isToggleFlag.property<Boolean>(flag).getOr(false),
    argument   = BooleanArgumentImpl(
      default     = BooleanFlagOptions::default.property(flag),
      isRequired  = BooleanFlagOptions::requireArg.property(flag),
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

internal class BooleanFlagImpl(
  longForm: Property<String>,
  shortForm: Property<Char>,
  isRequired: Property<Boolean>,
  isToggle: Boolean,
  argument: BooleanArgument
)
  : AbstractFlagImpl<BooleanArgument, Boolean>(longForm, shortForm, isRequired, argument)
  , BooleanFlag
{
  override val isToggleFlag: Boolean = isToggle

  override fun writeToString(builder: CliAppender) {
    if (!isToggleFlag)
      return super.writeToString(builder)

    if (argument.isDefault(builder.config))
      return

    if (!hasLongForm || builder.config.preferredFlagForm.isShort && hasShortForm) {
      builder.putShortFlag(shortForm, false)
    } else {
      builder.putLongFlag(longForm, false)
    }
  }
}
