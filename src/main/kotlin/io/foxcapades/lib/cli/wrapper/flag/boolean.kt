@file:Suppress("NOTHING_TO_INLINE")

package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.Flag
import io.foxcapades.lib.cli.wrapper.FlagOptions
import io.foxcapades.lib.cli.wrapper.NullableFlagOptions
import io.foxcapades.lib.cli.wrapper.arg.BooleanArgument
import io.foxcapades.lib.cli.wrapper.arg.BooleanArgumentImpl
import io.foxcapades.lib.cli.wrapper.serial.CliFlagWriter
import io.foxcapades.lib.cli.wrapper.serial.values.FlagPredicate
import io.foxcapades.lib.cli.wrapper.util.MutableProperty
import io.foxcapades.lib.cli.wrapper.util.Property
import io.foxcapades.lib.cli.wrapper.util.getOr
import io.foxcapades.lib.cli.wrapper.util.property

interface BooleanFlag : ScalarFlag<BooleanArgument, Boolean> {
  val isToggleFlag: Boolean
}

class BooleanFlagOptions : FlagOptions<Boolean>(Boolean::class) {
  var isToggleFlag by MutableProperty<Boolean>()
}

inline fun boolFlag(longForm: String, noinline action: BooleanFlagOptions.() -> Unit = {}) =
  booleanFlag(longForm, action)

inline fun boolFlag(shortForm: Char, noinline action: BooleanFlagOptions.() -> Unit = {}) =
  booleanFlag(shortForm, action)

inline fun boolFlag(noinline action: BooleanFlagOptions.() -> Unit = {}) =
  booleanFlag(action)

inline fun toggleFlag(longForm: String, noinline action: BooleanFlagOptions.() -> Unit = {}) =
  booleanFlag(longForm) {
    isToggleFlag = true
    action()
  }

inline fun toggleFlag(shortForm: Char, noinline action: BooleanFlagOptions.() -> Unit = {}) =
  booleanFlag(shortForm) {
    isToggleFlag = true
    action()
  }

inline fun toggleFlag(noinline action: BooleanFlagOptions.() -> Unit = {}) =
  booleanFlag {
    isToggleFlag = true
    action()
  }

inline fun booleanFlag(longForm: String, noinline action: BooleanFlagOptions.() -> Unit = {}) =
  booleanFlag { this.longForm = longForm; action() }

inline fun booleanFlag(shortForm: Char, noinline action: BooleanFlagOptions.() -> Unit = {}) =
  booleanFlag { this.shortForm = shortForm; action() }

fun booleanFlag(action: BooleanFlagOptions.() -> Unit = {}): BooleanFlag =
  BooleanFlagImpl(BooleanFlagOptions().also(action))

fun nullableBooleanFlag(action: NullableFlagOptions<Boolean>.() -> Unit = {}): Flag<Argument<Boolean?>, Boolean?> =
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

  constructor(opts: FlagOptions<Boolean>) : this(
    longForm   = BooleanFlagOptions::longForm.property(opts),
    shortForm  = BooleanFlagOptions::shortForm.property(opts),
    isRequired = BooleanFlagOptions::required.property(opts),
    filter     = BooleanFlagOptions::flagFilter.property(opts),
    isToggle   = BooleanFlagOptions::isToggleFlag.property<Boolean>(opts).getOr(false),
    argument   = BooleanArgumentImpl(opts.argument)
  )

  override fun writeToString(builder: CliFlagWriter<*, Boolean>) {
    if (!isToggleFlag)
      super.writeToString(builder)
    else if (argument.getOr(false))
      builder.writePreferredForm()
  }
}
