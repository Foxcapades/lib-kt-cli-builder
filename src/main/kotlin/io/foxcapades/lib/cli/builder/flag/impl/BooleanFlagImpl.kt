package io.foxcapades.lib.cli.builder.flag.impl

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.arg.impl.BasicArgumentImpl
import io.foxcapades.lib.cli.builder.flag.BooleanFlag
import io.foxcapades.lib.cli.builder.flag.BooleanFlagOptions
import io.foxcapades.lib.cli.builder.flag.FlagOptions
import io.foxcapades.lib.cli.builder.flag.filter.FlagPredicate
import io.foxcapades.lib.cli.builder.serial.CliFlagWriter
import io.foxcapades.lib.cli.builder.util.properties.Property
import io.foxcapades.lib.cli.builder.util.properties.getOr
import io.foxcapades.lib.cli.builder.util.properties.unsafeCast
import io.foxcapades.lib.cli.builder.util.reflect.property

internal class BooleanFlagImpl(
  longForm:   Property<String>,
  shortForm:  Property<Char>,
  isRequired: Property<Boolean>,
  filter:     Property<FlagPredicate<Boolean>>,
  isToggle:   Boolean,
  argument:   Argument<Boolean>
)
  : BasicFlagImpl<Boolean>(longForm, shortForm, isRequired, filter.unsafeCast(), argument)
  , BooleanFlag
{
  override val isToggleFlag: Boolean = isToggle

  constructor(opts: FlagOptions<Boolean>) : this(
    longForm   = BooleanFlagOptions::longForm.property(opts),
    shortForm  = BooleanFlagOptions::shortForm.property(opts),
    isRequired = BooleanFlagOptions::required.property(opts),
    filter     = BooleanFlagOptions::flagFilter.property(opts),
    isToggle   = BooleanFlagOptions::isToggleFlag.property<Boolean>(opts).getOr(false),
    argument   = BasicArgumentImpl.of(opts.argument)
  )

  override fun writeToString(writer: CliFlagWriter<*, Boolean>) {
    if (!isToggleFlag)
      super.writeToString(writer)
    else if (argument.getOr(false))
      writer.writePreferredForm()
  }
}
