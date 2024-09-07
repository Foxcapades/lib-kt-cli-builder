package io.foxcapades.lib.cli.builder.flag.impl

import io.foxcapades.lib.cli.builder.flag.FlagOptions
import io.foxcapades.lib.cli.builder.arg.BigIntegerArgument
import io.foxcapades.lib.cli.builder.arg.impl.BigIntegerArgumentImpl
import io.foxcapades.lib.cli.builder.flag.BigIntegerFlag
import io.foxcapades.lib.cli.builder.flag.filter.FlagPredicate
import io.foxcapades.lib.cli.builder.util.properties.Property
import io.foxcapades.lib.cli.builder.reflect.property
import java.math.BigInteger

internal class BigIntegerFlagImpl(
  longForm: Property<String>,
  shortForm: Property<Char>,
  isRequired: Property<Boolean>,
  filter: Property<FlagPredicate<BigIntegerFlag, BigIntegerArgument, BigInteger>>,
  argument:   BigIntegerArgument
)
  : AbstractFlagImpl<BigIntegerFlag, BigIntegerArgument, BigInteger>(
    longForm,
    shortForm,
    isRequired,
    filter,
    argument,
  ),
  BigIntegerFlag
{
  constructor(opts: FlagOptions<BigInteger>) : this(
    longForm   = FlagOptions<BigInteger>::longForm.property(opts),
    shortForm  = FlagOptions<BigInteger>::shortForm.property(opts),
    isRequired = FlagOptions<BigInteger>::required.property(opts),
    filter     = FlagOptions<BigInteger>::flagFilter.property(opts),
    argument   = BigIntegerArgumentImpl(opts.argument)
  )
}
