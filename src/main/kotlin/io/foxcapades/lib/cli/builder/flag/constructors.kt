@file:Suppress("NOTHING_TO_INLINE")
package io.foxcapades.lib.cli.builder.flag

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.arg.format.ArgumentFormatter
import io.foxcapades.lib.cli.builder.arg.format.NonNullGeneralStringifier
import io.foxcapades.lib.cli.builder.arg.format.unsafeCast
import io.foxcapades.lib.cli.builder.flag.impl.BasicFlagImpl
import io.foxcapades.lib.cli.builder.flag.impl.BooleanFlagImpl
import io.foxcapades.lib.cli.builder.flag.impl.UniversalFlagImpl
import java.io.File
import java.math.BigDecimal
import java.math.BigInteger
import java.nio.file.Path


// region BigDecimal

inline fun bigDecimalFlag(longForm: String, noinline action: FlagOptions<BigDecimal>.() -> Unit = {}) =
  bigDecimalFlag { this.longForm = longForm; action() }

@Suppress("NOTHING_TO_INLINE")
inline fun bigDecimalFlag(shortForm: Char, noinline action: FlagOptions<BigDecimal>.() -> Unit = {}) =
  bigDecimalFlag { this.shortForm = shortForm; action() }

fun bigDecimalFlag(action: FlagOptions<BigDecimal>.() -> Unit = {}): Flag<Argument<BigDecimal>, BigDecimal> =
  BasicFlagImpl.of(FlagOptions(BigDecimal::class).also {
    it.formatter = ArgumentFormatter(BigDecimal::toPlainString)
    it.action()
  })

fun nullableBigDecimalFlag(
  action: NullableFlagOptions<BigDecimal>.() -> Unit = {}
): Flag<Argument<BigDecimal?>, BigDecimal?> =
  UniversalFlagImpl.of(NullableFlagOptions(BigDecimal::class).also(action))

// endregion BigDecimal

// region BigInteger

inline fun bigIntegerFlag(longForm: String, noinline action: FlagOptions<BigInteger>.() -> Unit = {}) =
  bigIntegerFlag { this.longForm = longForm; action() }

inline fun bigIntegerFlag(shortForm: Char, noinline action: FlagOptions<BigInteger>.() -> Unit = {}) =
  bigIntegerFlag { this.shortForm = shortForm; action() }

fun bigIntegerFlag(action: FlagOptions<BigInteger>.() -> Unit = {}): Flag<Argument<BigInteger>, BigInteger> =
  BasicFlagImpl.of(FlagOptions(BigInteger::class).also {
    it.formatter = NonNullGeneralStringifier.unsafeCast()
    it.action()
  })

fun nullableBigIntegerFlag(
  action: NullableFlagOptions<BigInteger>.() -> Unit = {}
): Flag<Argument<BigInteger?>, BigInteger?> =
  UniversalFlagImpl.of(NullableFlagOptions(BigInteger::class).also(action))

// endregion BigInteger

// region Boolean

inline fun boolFlag(longForm: String, noinline action: BooleanFlagOptions.() -> Unit = {}) =
  booleanFlag(longForm, action)

inline fun boolFlag(shortForm: Char, noinline action: BooleanFlagOptions.() -> Unit = {}) =
  booleanFlag(shortForm, action)

inline fun boolFlag(noinline action: BooleanFlagOptions.() -> Unit = {}) =
  booleanFlag(action)

inline fun toggleFlag(longForm: String, noinline action: BooleanFlagOptions.() -> Unit = {}) =
  booleanFlag(longForm) {
    isToggleFlag = true
    action()
  }

inline fun toggleFlag(shortForm: Char, noinline action: BooleanFlagOptions.() -> Unit = {}) =
  booleanFlag(shortForm) {
    isToggleFlag = true
    action()
  }

inline fun toggleFlag(noinline action: BooleanFlagOptions.() -> Unit = {}) =
  booleanFlag {
    isToggleFlag = true
    action()
  }

inline fun booleanFlag(longForm: String, noinline action: BooleanFlagOptions.() -> Unit = {}) =
  booleanFlag { this.longForm = longForm; action() }

inline fun booleanFlag(shortForm: Char, noinline action: BooleanFlagOptions.() -> Unit = {}) =
  booleanFlag { this.shortForm = shortForm; action() }

fun booleanFlag(action: BooleanFlagOptions.() -> Unit = {}): BooleanFlag =
  BooleanFlagImpl(BooleanFlagOptions().also {
    it.formatter = NonNullGeneralStringifier.unsafeCast()
    it.action()
  })

fun nullableBooleanFlag(action: NullableFlagOptions<Boolean>.() -> Unit = {}): Flag<Argument<Boolean?>, Boolean?> =
  UniversalFlagImpl.of(NullableFlagOptions(Boolean::class).also(action))

// endregion Boolean

// region Byte


inline fun byteFlag(longForm: String, noinline action: FlagOptions<Byte>.() -> Unit = {}) =
  byteFlag { this.longForm = longForm; action() }

inline fun byteFlag(shortForm: Char, noinline action: FlagOptions<Byte>.() -> Unit = {}) =
  byteFlag { this.shortForm = shortForm; action() }

fun byteFlag(action: FlagOptions<Byte>.() -> Unit = {}): Flag<Argument<Byte>, Byte> =
  BasicFlagImpl.of(FlagOptions(Byte::class).also {
    it.formatter = NonNullGeneralStringifier.unsafeCast()
    it.action()
  })

fun nullableByteFlag(action: NullableFlagOptions<Byte>.() -> Unit = {}): Flag<Argument<Byte?>, Byte?> =
  UniversalFlagImpl.of(NullableFlagOptions(Byte::class).also(action))

// endregion Byte

// region Char


inline fun charFlag(longForm: String, noinline action: FlagOptions<Char>.() -> Unit = {}) =
  charFlag { this.longForm = longForm; action() }

inline fun charFlag(shortForm: Char, noinline action: FlagOptions<Char>.() -> Unit = {}) =
  charFlag { this.shortForm = shortForm; action() }

fun charFlag(action: FlagOptions<Char>.() -> Unit = {}): Flag<Argument<Char>, Char> =
  BasicFlagImpl.of(FlagOptions(Char::class).also {
    it.formatter = NonNullGeneralStringifier.unsafeCast()
    it.action()
  })

fun nullableCharFlag(action: NullableFlagOptions<Char>.() -> Unit = {}): Flag<Argument<Char?>, Char?> =
  UniversalFlagImpl.of(NullableFlagOptions(Char::class).also(action))

// endregion Char

// region Double

inline fun doubleFlag(longForm: String, noinline action: FlagOptions<Double>.() -> Unit = {}) =
  doubleFlag { this.longForm = longForm; action() }

inline fun doubleFlag(shortForm: Char, noinline action: FlagOptions<Double>.() -> Unit = {}) =
  doubleFlag { this.shortForm = shortForm; action() }

fun doubleFlag(action: FlagOptions<Double>.() -> Unit = {}): Flag<Argument<Double>, Double> =
  BasicFlagImpl.of(FlagOptions(Double::class).also {
    it.formatter = NonNullGeneralStringifier.unsafeCast()
    it.action()
  })

fun nullableDoubleFlag(action: NullableFlagOptions<Double>.() -> Unit = {}): Flag<Argument<Double?>, Double?> =
  UniversalFlagImpl.of(NullableFlagOptions(Double::class).also(action))

// endregion Double

// region Float

inline fun fileFlag(longForm: String, noinline action: FlagOptions<File>.() -> Unit = {}) =
  fileFlag { this.longForm = longForm; action() }

inline fun fileFlag(shortForm: Char, noinline action: FlagOptions<File>.() -> Unit = {}) =
  fileFlag { this.shortForm = shortForm; action() }

fun fileFlag(action: FlagOptions<File>.() -> Unit = {}): Flag<Argument<File>, File> =
  BasicFlagImpl.of(FlagOptions(File::class).also {
    it.shouldQuote = true
    it.formatter = NonNullGeneralStringifier.unsafeCast()
    it.action()
  })

fun nullableFileFlag(action: NullableFlagOptions<File>.() -> Unit = {}): Flag<Argument<File?>, File?> =
  UniversalFlagImpl.of(NullableFlagOptions(File::class).also(action))

// endregion Float

// region File

inline fun floatFlag(longForm: String, noinline action: FlagOptions<Float>.() -> Unit = {}) =
  floatFlag { this.longForm = longForm; action() }

inline fun floatFlag(shortForm: Char, noinline action: FlagOptions<Float>.() -> Unit = {}) =
  floatFlag { this.shortForm = shortForm; action() }

fun floatFlag(action: FlagOptions<Float>.() -> Unit = {}): Flag<Argument<Float>, Float> =
  BasicFlagImpl.of(FlagOptions(Float::class).also {
    it.formatter = NonNullGeneralStringifier.unsafeCast()
    it.action()
  })

fun nullableFloatFlag(action: NullableFlagOptions<Float>.() -> Unit = {}): Flag<Argument<Float?>, Float?> =
  UniversalFlagImpl.of(NullableFlagOptions(Float::class).also(action))

// endregion File

// region Int

inline fun intFlag(longForm: String, noinline action: FlagOptions<Int>.() -> Unit = {}) =
  intFlag { this.longForm = longForm; action() }

inline fun intFlag(shortForm: Char, noinline action: FlagOptions<Int>.() -> Unit = {}) =
  intFlag { this.shortForm = shortForm; action() }

fun intFlag(action: FlagOptions<Int>.() -> Unit = {}): Flag<Argument<Int>, Int> =
  BasicFlagImpl.of(FlagOptions(Int::class).also {
    it.formatter = NonNullGeneralStringifier.unsafeCast()
    it.action()
  })

fun nullableIntFlag(action: NullableFlagOptions<Int>.() -> Unit = {}): Flag<Argument<Int?>, Int?> =
  UniversalFlagImpl.of(NullableFlagOptions(Int::class).also(action))

// endregion Int

// region Long

inline fun longFlag(longForm: String, noinline action: FlagOptions<Long>.() -> Unit = {}) =
  longFlag { this.longForm = longForm; action() }

inline fun longFlag(shortForm: Char, noinline action: FlagOptions<Long>.() -> Unit = {}) =
  longFlag { this.shortForm = shortForm; action() }

fun longFlag(action: FlagOptions<Long>.() -> Unit = {}): Flag<Argument<Long>, Long> =
  BasicFlagImpl.of(FlagOptions(Long::class).also {
    it.formatter = NonNullGeneralStringifier.unsafeCast()
    it.action()
  })

fun nullableLongFlag(action: NullableFlagOptions<Long>.() -> Unit = {}): Flag<Argument<Long?>, Long?> =
  UniversalFlagImpl.of(NullableFlagOptions(Long::class).also(action))

// endregion Long

// region Path

inline fun pathFlag(longForm: String, noinline action: FlagOptions<Path>.() -> Unit = {}) =
  pathFlag { this.longForm = longForm; action() }

inline fun pathFlag(shortForm: Char, noinline action: FlagOptions<Path>.() -> Unit = {}) =
  pathFlag { this.shortForm = shortForm; action() }

fun pathFlag(action: FlagOptions<Path>.() -> Unit = {}): Flag<Argument<Path>, Path> =
  BasicFlagImpl.of(FlagOptions(Path::class).also {
    it.shouldQuote = true
    it.formatter = NonNullGeneralStringifier.unsafeCast()
    it.action()
  })

fun nullablePathFlag(action: NullableFlagOptions<Path>.() -> Unit = {}): Flag<Argument<Path?>, Path?> =
  UniversalFlagImpl.of(NullableFlagOptions(Path::class).also(action))

// endregion Path

// region Short

inline fun shortFlag(longForm: String, noinline action: FlagOptions<Short>.() -> Unit = {}) =
  shortFlag { this.longForm = longForm; action() }

inline fun shortFlag(shortForm: Char, noinline action: FlagOptions<Short>.() -> Unit = {}) =
  shortFlag { this.shortForm = shortForm; action() }

fun shortFlag(action: FlagOptions<Short>.() -> Unit = {}): Flag<Argument<Short>, Short> =
  BasicFlagImpl.of(FlagOptions(Short::class).also {
    it.formatter = NonNullGeneralStringifier.unsafeCast()
    it.action()
  })

fun nullableShortFlag(action: NullableFlagOptions<Short>.() -> Unit = {}): Flag<Argument<Short?>, Short?> =
  UniversalFlagImpl.of(NullableFlagOptions(Short::class).also(action))

// endregion Short

// region String

inline fun stringFlag(longForm: String, noinline action: FlagOptions<String>.() -> Unit = {}) =
  stringFlag { this.longForm = longForm; action() }

inline fun stringFlag(shortForm: Char, noinline action: FlagOptions<String>.() -> Unit = {}) =
  stringFlag { this.shortForm = shortForm; action() }

fun stringFlag(action: FlagOptions<String>.() -> Unit = {}): Flag<Argument<String>, String> =
  BasicFlagImpl.of(FlagOptions(String::class).also {
    it.shouldQuote = true
    it.formatter = NonNullGeneralStringifier.unsafeCast()
    it.action()
  })

fun nullableStringFlag(action: NullableFlagOptions<String>.() -> Unit = {}): Flag<Argument<String?>, String?> =
  UniversalFlagImpl.of(NullableFlagOptions(String::class).also(action))

// endregion String

// region UByte

inline fun ubyteFlag(longForm: String, noinline action: FlagOptions<UByte>.() -> Unit = {}) =
  ubyteFlag { this.longForm = longForm; action() }

inline fun ubyteFlag(shortForm: Char, noinline action: FlagOptions<UByte>.() -> Unit = {}) =
  ubyteFlag { this.shortForm = shortForm; action() }

fun ubyteFlag(action: FlagOptions<UByte>.() -> Unit = {}): Flag<Argument<UByte>, UByte> =
  BasicFlagImpl.of(FlagOptions(UByte::class).also {
    it.formatter = NonNullGeneralStringifier.unsafeCast()
    it.action()
  })

fun nullableUByteFlag(action: NullableFlagOptions<UByte>.() -> Unit = {}): Flag<Argument<UByte?>, UByte?> =
  UniversalFlagImpl.of(NullableFlagOptions(UByte::class).also(action))

// endregion UByte

// region UInt

inline fun uintFlag(longForm: String, noinline action: FlagOptions<UInt>.() -> Unit = {}) =
  uintFlag { this.longForm = longForm; action() }

inline fun uintFlag(shortForm: Char, noinline action: FlagOptions<UInt>.() -> Unit = {}) =
  uintFlag { this.shortForm = shortForm; action() }

fun uintFlag(action: FlagOptions<UInt>.() -> Unit = {}): Flag<Argument<UInt>, UInt> =
  BasicFlagImpl.of(FlagOptions(UInt::class).also {
    it.formatter = NonNullGeneralStringifier.unsafeCast()
    it.action()
  })

fun nullableUIntFlag(action: NullableFlagOptions<UInt>.() -> Unit = {}): Flag<Argument<UInt?>, UInt?> =
  UniversalFlagImpl.of(NullableFlagOptions(UInt::class).also(action))

// endregion UInt

// region ULong

inline fun ulongFlag(longForm: String, noinline action: FlagOptions<ULong>.() -> Unit = {}) =
  ulongFlag { this.longForm = longForm; action() }

inline fun ulongFlag(shortForm: Char, noinline action: FlagOptions<ULong>.() -> Unit = {}) =
  ulongFlag { this.shortForm = shortForm; action() }

fun ulongFlag(action: FlagOptions<ULong>.() -> Unit = {}): Flag<Argument<ULong>, ULong> =
  BasicFlagImpl.of(FlagOptions(ULong::class).also {
    it.formatter = NonNullGeneralStringifier.unsafeCast()
    it.action()
  })

fun nullableULongFlag(action: NullableFlagOptions<ULong>.() -> Unit = {}): Flag<Argument<ULong?>, ULong?> =
  UniversalFlagImpl.of(NullableFlagOptions(ULong::class).also(action))

// endregion ULong

// region UShort

inline fun ushortFlag(longForm: String, noinline action: FlagOptions<UShort>.() -> Unit = {}) =
  ushortFlag { this.longForm = longForm; action() }

inline fun ushortFlag(shortForm: Char, noinline action: FlagOptions<UShort>.() -> Unit = {}) =
  ushortFlag { this.shortForm = shortForm; action() }

fun ushortFlag(action: FlagOptions<UShort>.() -> Unit = {}): Flag<Argument<UShort>, UShort> =
  BasicFlagImpl.of(FlagOptions(UShort::class).also {
    it.formatter = NonNullGeneralStringifier.unsafeCast()
    it.action()
  })

fun nullableUShortFlag(action: NullableFlagOptions<UShort>.() -> Unit = {}): Flag<Argument<UShort?>, UShort?> =
  UniversalFlagImpl.of(NullableFlagOptions(UShort::class).also(action))

// endregion UShort
