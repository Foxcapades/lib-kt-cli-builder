package io.foxcapades.lib.cli.builder.arg

import io.foxcapades.lib.cli.builder.arg.impl.CharArgumentImpl
import io.foxcapades.lib.cli.builder.arg.impl.UniversalArgumentImpl

fun charArg(action: ArgOptions<Char>.() -> Unit): CharArgument =
  CharArgumentImpl(ArgOptions(Char::class).also(action))

fun nullableCharArg(action: NullableArgOptions<Char>.() -> Unit): Argument<Char?> =
  UniversalArgumentImpl.of(NullableArgOptions(Char::class).also(action))

