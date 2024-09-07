package io.foxcapades.lib.cli.builder.arg

import io.foxcapades.lib.cli.builder.arg.impl.ULongArgumentImpl
import io.foxcapades.lib.cli.builder.arg.impl.UniversalArgumentImpl

fun ulongArg(action: ArgOptions<ULong>.() -> Unit): ULongArgument =
  ULongArgumentImpl(ArgOptions(ULong::class).also(action))

fun nullableULongArg(action: NullableArgOptions<ULong>.() -> Unit): Argument<ULong?> =
  UniversalArgumentImpl.of(NullableArgOptions(ULong::class).also(action))
