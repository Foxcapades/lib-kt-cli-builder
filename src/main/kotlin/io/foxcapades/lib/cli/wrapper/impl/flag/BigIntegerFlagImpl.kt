package io.foxcapades.lib.cli.wrapper.impl.flag

import io.foxcapades.lib.cli.wrapper.arg.BigIntegerArgument
import io.foxcapades.lib.cli.wrapper.flag.BigIntegerFlag
import java.math.BigInteger

internal class BigIntegerFlagImpl : AbstractFlagImpl<BigIntegerArgument, BigInteger>, BigIntegerFlag {
  constructor(longForm: String, shortForm: Byte, isRequired: Boolean, argument: BigIntegerArgument)
    : super(longForm, shortForm, isRequired, argument)

  constructor(longForm: String, isRequired: Boolean, argument: BigIntegerArgument)
    : super(longForm, isRequired, argument)

  constructor(shortForm: Byte, isRequired: Boolean, argument: BigIntegerArgument)
    : super(shortForm, isRequired, argument)
}

