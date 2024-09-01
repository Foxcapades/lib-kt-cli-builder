package io.foxcapades.lib.cli.wrapper.impl.flag

import io.foxcapades.lib.cli.wrapper.arg.BigDecimalArgument
import io.foxcapades.lib.cli.wrapper.flag.BigDecimalFlag
import java.math.BigDecimal


internal class BigDecimalFlagImpl : AbstractFlagImpl<BigDecimalArgument, BigDecimal>, BigDecimalFlag {
  constructor(longForm: String, shortForm: Byte, isRequired: Boolean, argument: BigDecimalArgument)
    : super(longForm, shortForm, isRequired, argument)

  constructor(longForm: String, isRequired: Boolean, argument: BigDecimalArgument)
    : super(longForm, isRequired, argument)

  constructor(shortForm: Byte, isRequired: Boolean, argument: BigDecimalArgument)
    : super(shortForm, isRequired, argument)
}

