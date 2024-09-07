package io.foxcapades.lib.cli.builder.flag.impl

import io.foxcapades.lib.cli.builder.flag.FlagOptions
import io.foxcapades.lib.cli.builder.arg.BooleanArgument
import io.foxcapades.lib.cli.builder.arg.impl.BooleanArgumentImpl
import io.foxcapades.lib.cli.builder.flag.BooleanFlag
import io.foxcapades.lib.cli.builder.flag.BooleanFlagOptions
import io.foxcapades.lib.cli.builder.serial.CliFlagWriter
import io.foxcapades.lib.cli.builder.flag.filter.FlagPredicate
import io.foxcapades.lib.cli.builder.util.properties.Property
import io.foxcapades.lib.cli.builder.util.properties.getOr
import io.foxcapades.lib.cli.builder.reflect.property

internal class BooleanFlagImpl(
  longForm: Property<String>,
  shortForm: Property<Char>,
  isRequired: Property<Boolean>,
  filter: Property<FlagPredicate<BooleanFlag, BooleanArgument, Boolean>>,
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
