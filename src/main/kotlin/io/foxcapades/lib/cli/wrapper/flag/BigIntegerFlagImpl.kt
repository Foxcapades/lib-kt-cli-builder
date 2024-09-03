package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.BigIntegerArgument
import io.foxcapades.lib.cli.wrapper.util.Property
import java.math.BigInteger

internal class BigIntegerFlagImpl : AbstractFlagImpl<BigIntegerArgument, BigInteger>, BigIntegerFlag {
  constructor(
    longForm: Property<String>,
    shortForm: Property<Char>,
    isRequired: Property<Boolean>,
    argument: BigIntegerArgument,
  ) : super(
    longForm,
    shortForm,
    isRequired,
    argument,
  )

  constructor(longForm: String, shortForm: Char, isRequired: Boolean, argument: BigIntegerArgument)
    : super(longForm, shortForm, isRequired, argument)

  constructor(longForm: String, isRequired: Boolean, argument: BigIntegerArgument)
    : super(longForm, isRequired, argument)

  constructor(shortForm: Char, isRequired: Boolean, argument: BigIntegerArgument)
    : super(shortForm, isRequired, argument)
}
