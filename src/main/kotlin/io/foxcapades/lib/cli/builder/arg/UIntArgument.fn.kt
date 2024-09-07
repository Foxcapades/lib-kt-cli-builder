package io.foxcapades.lib.cli.builder.arg

import io.foxcapades.lib.cli.builder.arg.impl.UIntArgumentImpl
import io.foxcapades.lib.cli.builder.arg.impl.UniversalArgumentImpl

fun uintArg(action: ArgOptions<UInt>.() -> Unit): UIntArgument =
  UIntArgumentImpl(ArgOptions(UInt::class).also(action))

fun nullableUIntArg(action: NullableArgOptions<UInt>.() -> Unit): Argument<UInt?> {
  return UniversalArgumentImpl.of(NullableArgOptions(UInt::class).also(action))
}
