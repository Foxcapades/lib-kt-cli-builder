package io.foxcapades.lib.cli.builder.flag.impl

import io.foxcapades.lib.cli.builder.*
import io.foxcapades.lib.cli.builder.arg.BigDecimalArgument
import io.foxcapades.lib.cli.builder.arg.impl.BigDecimalArgumentImpl
import io.foxcapades.lib.cli.builder.flag.BigDecimalFlag
import io.foxcapades.lib.cli.builder.flag.FlagOptions
import io.foxcapades.lib.cli.builder.flag.filter.FlagPredicate
import io.foxcapades.lib.cli.builder.util.properties.Property
import io.foxcapades.lib.cli.builder.reflect.property
import java.math.BigDecimal

internal class BigDecimalFlagImpl(
  longForm: Property<String>,
  shortForm: Property<Char>,
  isRequired: Property<Boolean>,
  filter: Property<FlagPredicate<BigDecimalFlag, BigDecimalArgument, BigDecimal>>,
  argument:   BigDecimalArgument,
)
  : AbstractFlagImpl<BigDecimalFlag, BigDecimalArgument, BigDecimal>(
    longForm,
    shortForm,
    isRequired,
    filter,
    argument,
  )
  , BigDecimalFlag
{
  constructor(opts: FlagOptions<BigDecimal>) : this(
    longForm   = FlagOptions<BigDecimal>::longForm.property(opts),
    shortForm  = FlagOptions<BigDecimal>::shortForm.property(opts),
    isRequired = FlagOptions<BigDecimal>::required.property(opts),
    filter     = FlagOptions<BigDecimal>::flagFilter.property(opts),
    argument   = BigDecimalArgumentImpl(opts.argument)
  )
}
