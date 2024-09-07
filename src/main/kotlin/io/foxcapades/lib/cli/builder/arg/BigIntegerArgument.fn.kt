package io.foxcapades.lib.cli.builder.arg

import io.foxcapades.lib.cli.builder.arg.impl.BigIntegerArgumentImpl
import io.foxcapades.lib.cli.builder.arg.impl.UniversalArgumentImpl
import java.math.BigInteger

fun bigIntegerArg(action: ArgOptions<BigInteger>.() -> Unit): BigIntegerArgument =
  BigIntegerArgumentImpl(ArgOptions(BigInteger::class).also(action))

fun bigIntArg(action: ArgOptions<BigInteger>.() -> Unit): BigIntegerArgument =
  BigIntegerArgumentImpl(ArgOptions(BigInteger::class).also(action))

fun nullableBigIntegerArg(action: NullableArgOptions<BigInteger>.() -> Unit): Argument<BigInteger?> =
  UniversalArgumentImpl.of(NullableArgOptions(BigInteger::class).also(action))

fun nullableBigIntArg(action: NullableArgOptions<BigInteger>.() -> Unit): Argument<BigInteger?> =
  UniversalArgumentImpl.of(NullableArgOptions(BigInteger::class).also(action))
