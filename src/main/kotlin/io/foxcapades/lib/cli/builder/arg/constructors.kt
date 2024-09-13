package io.foxcapades.lib.cli.builder.arg

import io.foxcapades.lib.cli.builder.arg.format.ArgumentFormatter
import io.foxcapades.lib.cli.builder.arg.format.NonNullGeneralStringifier
import io.foxcapades.lib.cli.builder.arg.format.unsafeCast
import io.foxcapades.lib.cli.builder.arg.impl.*
import io.foxcapades.lib.cli.builder.util.properties.setIfAbsent
import io.foxcapades.lib.cli.builder.util.reflect.property
import java.io.File
import java.math.BigDecimal
import java.math.BigInteger
import java.nio.file.Path

// region BigDecimal

fun bigDecimalArg(action: ArgOptions<BigDecimal>.() -> Unit): DelegateArgument<BigDecimal> =
  BasicDelegateArgument.of(ArgOptions(BigDecimal::class).also {
    it.formatter = ArgumentFormatter(BigDecimal::toPlainString)
    it.action()
  })

// endregion BigDecimal


// region BigInteger

fun bigIntArg(action: ArgOptions<BigInteger>.() -> Unit): DelegateArgument<BigInteger> =
  bigIntegerArg(action)

fun bigIntegerArg(action: ArgOptions<BigInteger>.() -> Unit): DelegateArgument<BigInteger> =
  BasicDelegateArgument.of(ArgOptions(BigInteger::class).apply(action))

// endregion BigInteger

// region Boolean

fun boolArg(action: ArgOptions<Boolean>.() -> Unit): DelegateArgument<Boolean> =
  booleanArg(action)

fun booleanArg(action: ArgOptions<Boolean>.() -> Unit): DelegateArgument<Boolean> =
  BasicDelegateArgument.of(ArgOptions(Boolean::class).apply(action))

// endregion Boolean

// region Byte


fun byteArg(action: ArgOptions<Byte>.() -> Unit): DelegateArgument<Byte> =
  BasicDelegateArgument.of(ArgOptions(Byte::class).apply(action))

// endregion Byte

// region Char

fun charArg(action: ArgOptions<Char>.() -> Unit): DelegateArgument<Char> =
  BasicDelegateArgument.of(ArgOptions(Char::class).apply(action))

// endregion Char

// region Double

fun doubleArg(action: ArgOptions<Double>.() -> Unit): DelegateArgument<Double> =
  BasicDelegateArgument.of(ArgOptions(Double::class).apply(action))

// endregion Double

// region File

fun fileArg(action: ArgOptions<File>.() -> Unit): DelegateArgument<File> =
  BasicDelegateArgument.of(ArgOptions(File::class).also {
    it.shouldQuote = true
    it.action()
  })

// endregion File

// region Float

fun floatArg(action: ArgOptions<Float>.() -> Unit): DelegateArgument<Float> =
  BasicDelegateArgument.of(ArgOptions(Float::class).apply(action))

// endregion Float

// region Int

fun intArg(action: ArgOptions<Int>.() -> Unit): DelegateArgument<Int> =
  BasicDelegateArgument.of(ArgOptions(Int::class).apply(action))

// endregion Int

// region Long

fun longArg(action: ArgOptions<Long>.() -> Unit): DelegateArgument<Long> =
  BasicDelegateArgument.of(ArgOptions(Long::class).apply(action))

// endregion Long

// region Path

fun pathArg(action: ArgOptions<Path>.() -> Unit): DelegateArgument<Path> =
  BasicDelegateArgument.of(ArgOptions(Path::class).also {
    it.shouldQuote = true
    it.action()
  })

// endregion Path

// region Short

fun shortArg(action: ArgOptions<Short>.() -> Unit = {}): DelegateArgument<Short> =
  BasicDelegateArgument.of(ArgOptions(Short::class).apply(action))

// endregion Short

// region String

fun stringArg(action: ArgOptions<String>.() -> Unit): DelegateArgument<String> =
  BasicDelegateArgument.of(ArgOptions(String::class).also {
    it.shouldQuote = true
    it.action()
  })

// endregion String

// region UByte

fun ubyteArg(action: ArgOptions<UByte>.() -> Unit): DelegateArgument<UByte> =
  BasicDelegateArgument.of(ArgOptions(UByte::class).apply(action))

// endregion UByte

// region UInt

fun uintArg(action: ArgOptions<UInt>.() -> Unit): DelegateArgument<UInt> =
  BasicDelegateArgument.of(ArgOptions(UInt::class).apply(action))

// endregion UInt

// region ULong

fun ulongArg(action: ArgOptions<ULong>.() -> Unit): DelegateArgument<ULong> =
  BasicDelegateArgument.of(ArgOptions(ULong::class).apply(action))

// endregion ULong

// region UShort

fun ushortArg(action: ArgOptions<UShort>.() -> Unit): DelegateArgument<UShort> =
  BasicDelegateArgument.of(ArgOptions(UShort::class).apply(action))

// endregion UShort
