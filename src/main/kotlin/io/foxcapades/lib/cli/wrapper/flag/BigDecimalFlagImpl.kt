package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.BigDecimalArgument
import io.foxcapades.lib.cli.wrapper.util.Property
import java.math.BigDecimal

internal class BigDecimalFlagImpl : AbstractFlagImpl<BigDecimalArgument, BigDecimal>, BigDecimalFlag {
  constructor(
    longForm: Property<String>,
    shortForm: Property<Char>,
    isRequired: Property<Boolean>,
    argument: BigDecimalArgument,
  ) : super(
    longForm,
    shortForm,
    isRequired,
    argument,
  )

  constructor(longForm: String, shortForm: Char, isRequired: Boolean, argument: BigDecimalArgument)
    : super(longForm, shortForm, isRequired, argument)

  constructor(longForm: String, isRequired: Boolean, argument: BigDecimalArgument)
    : super(longForm, isRequired, argument)

  constructor(shortForm: Char, isRequired: Boolean, argument: BigDecimalArgument)
    : super(shortForm, isRequired, argument)
}
