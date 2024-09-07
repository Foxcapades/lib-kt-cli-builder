package io.foxcapades.lib.cli.builder.arg

import io.foxcapades.lib.cli.builder.arg.impl.ByteArgumentImpl
import io.foxcapades.lib.cli.builder.arg.impl.UniversalArgumentImpl

fun byteArg(action: ArgOptions<Byte>.() -> Unit): ByteArgument =
  ByteArgumentImpl(ArgOptions(Byte::class).also(action))

fun nullableByteArg(action: NullableArgOptions<Byte>.() -> Unit): Argument<Byte?> =
  UniversalArgumentImpl.of(NullableArgOptions(Byte::class).also(action))

