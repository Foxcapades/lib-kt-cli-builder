package io.foxcapades.lib.cli.builder.arg

import io.foxcapades.lib.cli.builder.arg.impl.IntArgumentImpl
import io.foxcapades.lib.cli.builder.arg.impl.UniversalArgumentImpl

fun intArg(action: ArgOptions<Int>.() -> Unit): IntArgument =
  IntArgumentImpl(ArgOptions(Int::class).also(action))

fun nullableIntArg(action: NullableArgOptions<Int>.() -> Unit): Argument<Int?> =
  UniversalArgumentImpl.of(NullableArgOptions(Int::class).also(action))
