package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.ArgOptions
import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.NullableArgOptions
import io.foxcapades.lib.cli.wrapper.util.property
import io.foxcapades.lib.cli.wrapper.util.setIfAbsent
import java.math.BigDecimal

fun bigDecimalArg(action: ArgOptions<BigDecimal>.() -> Unit): BigDecimalArgument {
  val opts = ArgOptions(BigDecimal::class).also(action)

  return BigDecimalArgumentImpl(
    default     = ArgOptions<BigDecimal>::default.property(opts),
    shouldQuote = ArgOptions<BigDecimal>::shouldQuote.property(opts),
    formatter   = ArgOptions<BigDecimal>::formatter.property(opts),
  )
}

fun nullableBigDecimalArg(action: NullableArgOptions<BigDecimal>.() -> Unit): Argument<BigDecimal?> {
  val opts = NullableArgOptions(BigDecimal::class).also(action)

  return GeneralArgumentImpl(
    type        = BigDecimal::class,
    nullable    = true,
    default     = NullableArgOptions<BigDecimal>::default.property(opts),
    shouldQuote = NullableArgOptions<BigDecimal>::shouldQuote.property<Boolean>(opts).setIfAbsent(false),
    isRequired  = NullableArgOptions<BigDecimal>::requireArg.property(opts),
    formatter   = NullableArgOptions<BigDecimal>::formatter.property(opts)
  )
}
