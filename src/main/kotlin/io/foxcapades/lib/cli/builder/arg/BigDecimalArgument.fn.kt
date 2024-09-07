package io.foxcapades.lib.cli.builder.arg

import io.foxcapades.lib.cli.builder.arg.impl.BigDecimalArgumentImpl
import io.foxcapades.lib.cli.builder.arg.impl.UniversalArgumentImpl
import java.math.BigDecimal

fun bigDecimalArg(action: ArgOptions<BigDecimal>.() -> Unit): BigDecimalArgument =
  BigDecimalArgumentImpl(ArgOptions(BigDecimal::class).also(action))

fun nullableBigDecimalArg(action: NullableArgOptions<BigDecimal>.() -> Unit): Argument<BigDecimal?> =
  UniversalArgumentImpl.of(NullableArgOptions(BigDecimal::class).also(action))

