package io.foxcapades.lib.cli.builder.arg

import io.foxcapades.lib.cli.builder.arg.impl.UShortArgumentImpl
import io.foxcapades.lib.cli.builder.arg.impl.UniversalArgumentImpl

fun ushortArg(action: ArgOptions<UShort>.() -> Unit): UShortArgument =
  UShortArgumentImpl(ArgOptions(UShort::class).also(action))

fun nullableUShortArg(action: NullableArgOptions<UShort>.() -> Unit): Argument<UShort?> =
  UniversalArgumentImpl.of(NullableArgOptions(UShort::class).also(action))
