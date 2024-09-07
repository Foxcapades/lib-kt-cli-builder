package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.ArgOptions
import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.NullableArgOptions

fun doubleArg(action: ArgOptions<Double>.() -> Unit): DoubleArgument =
  DoubleArgumentImpl(ArgOptions(Double::class).also(action))

fun nullableDoubleArg(action: NullableArgOptions<Double>.() -> Unit): Argument<Double?> =
  GeneralArgumentImpl.of(NullableArgOptions(Double::class).also(action))

