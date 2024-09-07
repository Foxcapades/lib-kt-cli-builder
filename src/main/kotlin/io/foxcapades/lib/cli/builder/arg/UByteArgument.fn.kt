package io.foxcapades.lib.cli.builder.arg

import io.foxcapades.lib.cli.builder.arg.impl.UByteArgumentImpl
import io.foxcapades.lib.cli.builder.arg.impl.UniversalArgumentImpl

fun ubyteArg(action: ArgOptions<UByte>.() -> Unit): UByteArgument =
  UByteArgumentImpl(ArgOptions(UByte::class).also(action))

fun nullableUByteArg(action: NullableArgOptions<UByte>.() -> Unit): Argument<UByte?> =
  UniversalArgumentImpl.of(NullableArgOptions(UByte::class).also(action))
