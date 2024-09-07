package io.foxcapades.lib.cli.builder.arg

import io.foxcapades.lib.cli.builder.arg.impl.DoubleArgumentImpl
import io.foxcapades.lib.cli.builder.arg.impl.UniversalArgumentImpl

fun doubleArg(action: ArgOptions<Double>.() -> Unit): DoubleArgument =
  DoubleArgumentImpl(ArgOptions(Double::class).also(action))

fun nullableDoubleArg(action: NullableArgOptions<Double>.() -> Unit): Argument<Double?> =
  UniversalArgumentImpl.of(NullableArgOptions(Double::class).also(action))

