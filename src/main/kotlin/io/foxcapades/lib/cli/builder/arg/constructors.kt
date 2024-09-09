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

fun bigDecimalArg(action: ArgOptions<BigDecimal>.() -> Unit): Argument<BigDecimal> =
  BasicArgumentImpl.of(ArgOptions(BigDecimal::class).also {
    it.formatter = ArgumentFormatter(BigDecimal::toPlainString)
    it.action()
  })

fun nullableBigDecimalArg(action: NullableArgOptions<BigDecimal>.() -> Unit): Argument<BigDecimal?> =
  UniversalArgumentImpl.of(NullableArgOptions(BigDecimal::class).also(action))

// endregion BigDecimal


// region BigInteger

fun bigIntArg(action: ArgOptions<BigInteger>.() -> Unit): Argument<BigInteger> =
  bigIntegerArg(action)

fun bigIntegerArg(action: ArgOptions<BigInteger>.() -> Unit): Argument<BigInteger> =
  BasicArgumentImpl.of(ArgOptions(BigInteger::class).also {
    it.formatter = NonNullGeneralStringifier.unsafeCast()
    it.action()
  })

fun nullableBigIntArg(action: NullableArgOptions<BigInteger>.() -> Unit): Argument<BigInteger?> =
  nullableBigIntegerArg(action)

fun nullableBigIntegerArg(action: NullableArgOptions<BigInteger>.() -> Unit): Argument<BigInteger?> =
  UniversalArgumentImpl.of(NullableArgOptions(BigInteger::class).also(action))

// endregion BigInteger

// region Boolean

fun boolArg(action: ArgOptions<Boolean>.() -> Unit): Argument<Boolean> =
  booleanArg(action)

fun booleanArg(action: ArgOptions<Boolean>.() -> Unit): Argument<Boolean> =
  BasicArgumentImpl.of(ArgOptions(Boolean::class).also {
    it.formatter = NonNullGeneralStringifier.unsafeCast()
    it.action()
  })

fun nullableBoolArg(action: NullableArgOptions<Boolean>.() -> Unit): Argument<Boolean?> =
  nullableBooleanArg(action)

fun nullableBooleanArg(action: NullableArgOptions<Boolean>.() -> Unit): Argument<Boolean?> =
  UniversalArgumentImpl.of(NullableArgOptions(Boolean::class).also(action))


// endregion Boolean

// region Byte


fun byteArg(action: ArgOptions<Byte>.() -> Unit): Argument<Byte> =
  BasicArgumentImpl.of(ArgOptions(Byte::class).also {
    it.formatter = NonNullGeneralStringifier.unsafeCast()
    it.action()
  })

fun nullableByteArg(action: NullableArgOptions<Byte>.() -> Unit): Argument<Byte?> =
  UniversalArgumentImpl.of(NullableArgOptions(Byte::class).also(action))

// endregion Byte

// region Char

fun charArg(action: ArgOptions<Char>.() -> Unit): Argument<Char> =
  BasicArgumentImpl.of(ArgOptions(Char::class).also {
    it.formatter = NonNullGeneralStringifier.unsafeCast()
    it.action()
  })

fun nullableCharArg(action: NullableArgOptions<Char>.() -> Unit): Argument<Char?> =
  UniversalArgumentImpl.of(NullableArgOptions(Char::class).also(action))

// endregion Char

// region Double

fun doubleArg(action: ArgOptions<Double>.() -> Unit): Argument<Double> =
  BasicArgumentImpl.of(ArgOptions(Double::class).also {
    it.formatter = NonNullGeneralStringifier.unsafeCast()
    it.action()
  })

fun nullableDoubleArg(action: NullableArgOptions<Double>.() -> Unit): Argument<Double?> =
  UniversalArgumentImpl.of(NullableArgOptions(Double::class).also(action))

// endregion Double

// region File

fun fileArg(action: ArgOptions<File>.() -> Unit): Argument<File> =
  BasicArgumentImpl.of(ArgOptions(File::class).also {
    it.shouldQuote = true
    it.formatter = NonNullGeneralStringifier.unsafeCast()
    it.action()
  })

fun nullableFileArg(action: NullableArgOptions<File>.() -> Unit): Argument<File?> =
  UniversalArgumentImpl.of(NullableArgOptions(File::class).also {
    it.action()
    NullableArgOptions<File>::shouldQuote.property<Boolean>(it).setIfAbsent(true)
  })

// endregion File

// region Float

fun floatArg(action: ArgOptions<Float>.() -> Unit): Argument<Float> =
  BasicArgumentImpl.of(ArgOptions(Float::class).also {
    it.formatter = NonNullGeneralStringifier.unsafeCast()
    it.action()
  })

fun nullableFloatArg(action: NullableArgOptions<Float>.() -> Unit): Argument<Float?> =
  UniversalArgumentImpl.of(NullableArgOptions(Float::class).also(action))

// endregion Float

// region Int

fun intArg(action: ArgOptions<Int>.() -> Unit): Argument<Int> =
  BasicArgumentImpl.of(ArgOptions(Int::class).also {
    it.formatter = NonNullGeneralStringifier.unsafeCast()
    it.action()
  })

fun nullableIntArg(action: NullableArgOptions<Int>.() -> Unit): Argument<Int?> =
  UniversalArgumentImpl.of(NullableArgOptions(Int::class).also(action))

// endregion Int

// region Long

fun longArg(action: ArgOptions<Long>.() -> Unit): Argument<Long> =
  BasicArgumentImpl.of(ArgOptions(Long::class).also {
    it.formatter = NonNullGeneralStringifier.unsafeCast()
    it.action()
  })

fun nullableLongArg(action: NullableArgOptions<Long>.() -> Unit): Argument<Long?> =
  UniversalArgumentImpl.of(NullableArgOptions(Long::class).also(action))

// endregion Long

// region Path

fun pathArg(action: ArgOptions<Path>.() -> Unit): Argument<Path> =
  BasicArgumentImpl.of(ArgOptions(Path::class).also {
    it.shouldQuote = true
    it.formatter = NonNullGeneralStringifier.unsafeCast()
    it.action()
  })

fun nullablePathArg(action: NullableArgOptions<Path>.() -> Unit): Argument<Path?> =
  UniversalArgumentImpl.of(NullableArgOptions(Path::class).also {
    it.action()
    NullableArgOptions<Path>::shouldQuote.property<Boolean>(it).setIfAbsent(true)
  })

// endregion Path

// region Short

fun shortArg(action: ArgOptions<Short>.() -> Unit = {}): Argument<Short> =
  BasicArgumentImpl.of(ArgOptions(Short::class).also {
    it.formatter = NonNullGeneralStringifier.unsafeCast()
    it.action()
  })

fun nullableShortArg(action: NullableArgOptions<Short>.() -> Unit = {}): Argument<Short?> {
  val opts = NullableArgOptions(Short::class).also(action)

  return UniversalArgumentImpl(
    type        = Short::class,
    nullable    = true,
    default     = NullableArgOptions<Short>::default.property(opts),
    shouldQuote = NullableArgOptions<Short>::shouldQuote.property<Boolean>(opts).setIfAbsent(false),
    isRequired  = NullableArgOptions<Short>::required.property(opts),
    formatter   = NullableArgOptions<Short>::formatter.property(opts),
    filter      = NullableArgOptions<Short>::filter.property(opts),
  )
}

// endregion Short

// region String

fun stringArg(action: ArgOptions<String>.() -> Unit): Argument<String> =
  BasicArgumentImpl.of(ArgOptions(String::class).also {
    it.shouldQuote = true
    it.formatter = NonNullGeneralStringifier.unsafeCast()
    it.action()
  })

fun nullableStringArg(action: NullableArgOptions<String>.() -> Unit): Argument<String?> =
  UniversalArgumentImpl.of(NullableArgOptions(String::class).also {
    it.action()
    NullableArgOptions<String>::shouldQuote.property<Boolean>(it).setIfAbsent(true)
  })

// endregion String

// region UByte

fun ubyteArg(action: ArgOptions<UByte>.() -> Unit): Argument<UByte> =
  BasicArgumentImpl.of(ArgOptions(UByte::class).also {
    it.formatter = NonNullGeneralStringifier.unsafeCast()
    it.action()
  })

fun nullableUByteArg(action: NullableArgOptions<UByte>.() -> Unit): Argument<UByte?> =
  UniversalArgumentImpl.of(NullableArgOptions(UByte::class).also(action))

// endregion UByte

// region UInt

fun uintArg(action: ArgOptions<UInt>.() -> Unit): Argument<UInt> =
  BasicArgumentImpl.of(ArgOptions(UInt::class).also {
    it.formatter = NonNullGeneralStringifier.unsafeCast()
    it.action()
  })

fun nullableUIntArg(action: NullableArgOptions<UInt>.() -> Unit): Argument<UInt?> =
  UniversalArgumentImpl.of(NullableArgOptions(UInt::class).also(action))

// endregion UInt

// region ULong

fun ulongArg(action: ArgOptions<ULong>.() -> Unit): Argument<ULong> =
  BasicArgumentImpl.of(ArgOptions(ULong::class).also {
    it.formatter = NonNullGeneralStringifier.unsafeCast()
    it.action()
  })

fun nullableULongArg(action: NullableArgOptions<ULong>.() -> Unit): Argument<ULong?> =
  UniversalArgumentImpl.of(NullableArgOptions(ULong::class).also(action))

// endregion ULong

// region UShort

fun ushortArg(action: ArgOptions<UShort>.() -> Unit): Argument<UShort> =
  BasicArgumentImpl.of(ArgOptions(UShort::class).also {
    it.formatter = NonNullGeneralStringifier.unsafeCast()
    it.action()
  })

fun nullableUShortArg(action: NullableArgOptions<UShort>.() -> Unit): Argument<UShort?> =
  UniversalArgumentImpl.of(NullableArgOptions(UShort::class).also(action))

// endregion UShort
