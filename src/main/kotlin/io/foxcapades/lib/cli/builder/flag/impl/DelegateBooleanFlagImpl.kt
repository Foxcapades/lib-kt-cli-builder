package io.foxcapades.lib.cli.builder.flag.impl

import io.foxcapades.kt.prop.delegation.Property
import io.foxcapades.kt.prop.delegation.getOr
import io.foxcapades.lib.cli.builder.arg.DelegateArgument
import io.foxcapades.lib.cli.builder.arg.impl.BasicDelegateArgument
import io.foxcapades.lib.cli.builder.flag.BooleanFlagOptions
import io.foxcapades.lib.cli.builder.flag.DelegateBooleanFlag
import io.foxcapades.lib.cli.builder.flag.FlagOptions
import io.foxcapades.lib.cli.builder.flag.filter.FlagPredicate
import io.foxcapades.lib.cli.builder.serial.CliFlagWriter
import io.foxcapades.lib.cli.builder.util.reflect.property

internal class DelegateBooleanFlagImpl(
  longForm:   Property<String>,
  shortForm:  Property<Char>,
  isRequired: Property<Boolean>,
  filter:     Property<FlagPredicate<Boolean>>,
  isToggle:   Boolean,
  argument:   DelegateArgument<Boolean>
)
  : BasicDelegateFlag<Boolean>(longForm, shortForm, isRequired, filter, argument)
  , DelegateBooleanFlag
{
  override val isToggleFlag: Boolean = isToggle

  constructor(opts: FlagOptions<Boolean>) : this(
    longForm   = BooleanFlagOptions::longForm.property(opts),
    shortForm  = BooleanFlagOptions::shortForm.property(opts),
    isRequired = BooleanFlagOptions::required.property(opts),
    filter     = BooleanFlagOptions::flagFilter.property(opts),
    isToggle   = BooleanFlagOptions::isToggleFlag.property<Boolean>(opts).getOr(false),
    argument   = BasicDelegateArgument.of(opts.argument)
  )

  override fun writeToString(writer: CliFlagWriter<*, Boolean>) {
    if (!isToggleFlag)
      super.writeToString(writer)
    else if (argument.getOr(false))
      writer.writePreferredForm()
  }
}
