package io.foxcapades.lib.cli.builder.arg

import io.foxcapades.lib.cli.builder.arg.impl.BooleanArgumentImpl
import io.foxcapades.lib.cli.builder.arg.impl.UniversalArgumentImpl

fun booleanArg(action: ArgOptions<Boolean>.() -> Unit): BooleanArgument =
  BooleanArgumentImpl(ArgOptions(Boolean::class).also(action))

fun boolArg(action: ArgOptions<Boolean>.() -> Unit): BooleanArgument =
  BooleanArgumentImpl(ArgOptions(Boolean::class).also(action))

fun nullableBooleanArg(action: NullableArgOptions<Boolean>.() -> Unit): Argument<Boolean?> =
  UniversalArgumentImpl.of(NullableArgOptions(Boolean::class).also(action))

fun nullableBoolArg(action: NullableArgOptions<Boolean>.() -> Unit): Argument<Boolean?> =
  UniversalArgumentImpl.of(NullableArgOptions(Boolean::class).also(action))
