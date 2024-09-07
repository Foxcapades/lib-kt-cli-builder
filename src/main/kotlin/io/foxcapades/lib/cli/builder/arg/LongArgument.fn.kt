package io.foxcapades.lib.cli.builder.arg

import io.foxcapades.lib.cli.builder.arg.impl.LongArgumentImpl
import io.foxcapades.lib.cli.builder.arg.impl.UniversalArgumentImpl

fun longArg(action: ArgOptions<Long>.() -> Unit): LongArgument =
  LongArgumentImpl(ArgOptions(Long::class).also(action))

fun nullableLongArg(action: NullableArgOptions<Long>.() -> Unit): Argument<Long?> =
  UniversalArgumentImpl.of(NullableArgOptions(Long::class).also(action))
