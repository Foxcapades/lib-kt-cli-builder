package io.foxcapades.lib.cli.builder.arg

import io.foxcapades.lib.cli.builder.arg.impl.FloatArgumentImpl
import io.foxcapades.lib.cli.builder.arg.impl.UniversalArgumentImpl

fun floatArg(action: ArgOptions<Float>.() -> Unit): FloatArgument =
  FloatArgumentImpl(ArgOptions(Float::class).also(action))

fun nullableFloatArg(action: NullableArgOptions<Float>.() -> Unit): Argument<Float?> =
  UniversalArgumentImpl.of(NullableArgOptions(Float::class).also(action))
