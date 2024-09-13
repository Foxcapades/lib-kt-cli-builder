@file:Suppress("NOTHING_TO_INLINE")
package io.foxcapades.lib.cli.builder.flag

import io.foxcapades.lib.cli.builder.arg.format.ArgumentFormatter
import io.foxcapades.lib.cli.builder.arg.format.NonNullGeneralStringifier
import io.foxcapades.lib.cli.builder.arg.format.unsafeCast
import io.foxcapades.lib.cli.builder.flag.impl.BasicDelegateFlag
import io.foxcapades.lib.cli.builder.flag.impl.DelegateBooleanFlagImpl
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

fun bigDecimalFlag(action: FlagOptions<BigDecimal>.() -> Unit = {}): DelegateFlag<BigDecimal> =
  BasicDelegateFlag.of(FlagOptions(BigDecimal::class).also {
    it.formatter = ArgumentFormatter(BigDecimal::toPlainString)
    it.action()
  })

// endregion BigDecimal

// region BigInteger

inline fun bigIntFlag(longForm: String, noinline action: FlagOptions<BigInteger>.() -> Unit = {}) =
  bigIntegerFlag(longForm, action)

inline fun bigIntFlag(shortForm: Char, noinline action: FlagOptions<BigInteger>.() -> Unit = {}) =
  bigIntegerFlag(shortForm, action)

inline fun bigIntFlag(noinline action: FlagOptions<BigInteger>.() -> Unit = {}) =
  bigIntegerFlag(action)


inline fun bigIntegerFlag(longForm: String, noinline action: FlagOptions<BigInteger>.() -> Unit = {}) =
  bigIntegerFlag { this.longForm = longForm; action() }

inline fun bigIntegerFlag(shortForm: Char, noinline action: FlagOptions<BigInteger>.() -> Unit = {}) =
  bigIntegerFlag { this.shortForm = shortForm; action() }

fun bigIntegerFlag(action: FlagOptions<BigInteger>.() -> Unit = {}): DelegateFlag<BigInteger> =
  BasicDelegateFlag.of(FlagOptions(BigInteger::class).also {
    it.formatter = NonNullGeneralStringifier.unsafeCast()
    it.action()
  })

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

fun booleanFlag(action: BooleanFlagOptions.() -> Unit = {}): DelegateBooleanFlag =
  DelegateBooleanFlagImpl(BooleanFlagOptions().also {
    it.formatter = NonNullGeneralStringifier.unsafeCast()
    it.action()
  })

// endregion Boolean

// region Byte


inline fun byteFlag(longForm: String, noinline action: FlagOptions<Byte>.() -> Unit = {}) =
  byteFlag { this.longForm = longForm; action() }

inline fun byteFlag(shortForm: Char, noinline action: FlagOptions<Byte>.() -> Unit = {}) =
  byteFlag { this.shortForm = shortForm; action() }

fun byteFlag(action: FlagOptions<Byte>.() -> Unit = {}): DelegateFlag<Byte> =
  BasicDelegateFlag.of(FlagOptions(Byte::class).also {
    it.formatter = NonNullGeneralStringifier.unsafeCast()
    it.action()
  })

// endregion Byte

// region Char


inline fun charFlag(longForm: String, noinline action: FlagOptions<Char>.() -> Unit = {}) =
  charFlag { this.longForm = longForm; action() }

inline fun charFlag(shortForm: Char, noinline action: FlagOptions<Char>.() -> Unit = {}) =
  charFlag { this.shortForm = shortForm; action() }

fun charFlag(action: FlagOptions<Char>.() -> Unit = {}): DelegateFlag<Char> =
  BasicDelegateFlag.of(FlagOptions(Char::class).also {
    it.formatter = NonNullGeneralStringifier.unsafeCast()
    it.action()
  })

// endregion Char

// region Double

inline fun doubleFlag(longForm: String, noinline action: FlagOptions<Double>.() -> Unit = {}) =
  doubleFlag { this.longForm = longForm; action() }

inline fun doubleFlag(shortForm: Char, noinline action: FlagOptions<Double>.() -> Unit = {}) =
  doubleFlag { this.shortForm = shortForm; action() }

fun doubleFlag(action: FlagOptions<Double>.() -> Unit = {}): DelegateFlag<Double> =
  BasicDelegateFlag.of(FlagOptions(Double::class).also {
    it.formatter = NonNullGeneralStringifier.unsafeCast()
    it.action()
  })

// endregion Double

// region Float

inline fun fileFlag(longForm: String, noinline action: FlagOptions<File>.() -> Unit = {}) =
  fileFlag { this.longForm = longForm; action() }

inline fun fileFlag(shortForm: Char, noinline action: FlagOptions<File>.() -> Unit = {}) =
  fileFlag { this.shortForm = shortForm; action() }

fun fileFlag(action: FlagOptions<File>.() -> Unit = {}): DelegateFlag<File> =
  BasicDelegateFlag.of(FlagOptions(File::class).also {
    it.shouldQuote = true
    it.formatter = NonNullGeneralStringifier.unsafeCast()
    it.action()
  })

// endregion Float

// region File

inline fun floatFlag(longForm: String, noinline action: FlagOptions<Float>.() -> Unit = {}) =
  floatFlag { this.longForm = longForm; action() }

inline fun floatFlag(shortForm: Char, noinline action: FlagOptions<Float>.() -> Unit = {}) =
  floatFlag { this.shortForm = shortForm; action() }

fun floatFlag(action: FlagOptions<Float>.() -> Unit = {}): DelegateFlag<Float> =
  BasicDelegateFlag.of(FlagOptions(Float::class).also {
    it.formatter = NonNullGeneralStringifier.unsafeCast()
    it.action()
  })

// endregion File

// region Int

inline fun intFlag(longForm: String, noinline action: FlagOptions<Int>.() -> Unit = {}) =
  intFlag { this.longForm = longForm; action() }

inline fun intFlag(shortForm: Char, noinline action: FlagOptions<Int>.() -> Unit = {}) =
  intFlag { this.shortForm = shortForm; action() }

fun intFlag(action: FlagOptions<Int>.() -> Unit = {}): DelegateFlag<Int> =
  BasicDelegateFlag.of(FlagOptions(Int::class).also {
    it.formatter = NonNullGeneralStringifier.unsafeCast()
    it.action()
  })

// endregion Int

// region Long

inline fun longFlag(longForm: String, noinline action: FlagOptions<Long>.() -> Unit = {}) =
  longFlag { this.longForm = longForm; action() }

inline fun longFlag(shortForm: Char, noinline action: FlagOptions<Long>.() -> Unit = {}) =
  longFlag { this.shortForm = shortForm; action() }

fun longFlag(action: FlagOptions<Long>.() -> Unit = {}): DelegateFlag<Long> =
  BasicDelegateFlag.of(FlagOptions(Long::class).also {
    it.formatter = NonNullGeneralStringifier.unsafeCast()
    it.action()
  })

// endregion Long

// region Path

inline fun pathFlag(longForm: String, noinline action: FlagOptions<Path>.() -> Unit = {}) =
  pathFlag { this.longForm = longForm; action() }

inline fun pathFlag(shortForm: Char, noinline action: FlagOptions<Path>.() -> Unit = {}) =
  pathFlag { this.shortForm = shortForm; action() }

fun pathFlag(action: FlagOptions<Path>.() -> Unit = {}): DelegateFlag<Path> =
  BasicDelegateFlag.of(FlagOptions(Path::class).also {
    it.shouldQuote = true
    it.formatter = NonNullGeneralStringifier.unsafeCast()
    it.action()
  })

// endregion Path

// region Short

inline fun shortFlag(longForm: String, noinline action: FlagOptions<Short>.() -> Unit = {}) =
  shortFlag { this.longForm = longForm; action() }

inline fun shortFlag(shortForm: Char, noinline action: FlagOptions<Short>.() -> Unit = {}) =
  shortFlag { this.shortForm = shortForm; action() }

fun shortFlag(action: FlagOptions<Short>.() -> Unit = {}): DelegateFlag<Short> =
  BasicDelegateFlag.of(FlagOptions(Short::class).also {
    it.formatter = NonNullGeneralStringifier.unsafeCast()
    it.action()
  })

// endregion Short

// region String

inline fun stringFlag(longForm: String, noinline action: FlagOptions<String>.() -> Unit = {}) =
  stringFlag { this.longForm = longForm; action() }

inline fun stringFlag(shortForm: Char, noinline action: FlagOptions<String>.() -> Unit = {}) =
  stringFlag { this.shortForm = shortForm; action() }

fun stringFlag(action: FlagOptions<String>.() -> Unit = {}): DelegateFlag<String> =
  BasicDelegateFlag.of(FlagOptions(String::class).also {
    it.shouldQuote = true
    it.formatter = NonNullGeneralStringifier.unsafeCast()
    it.action()
  })

// endregion String

// region UByte

inline fun ubyteFlag(longForm: String, noinline action: FlagOptions<UByte>.() -> Unit = {}) =
  ubyteFlag { this.longForm = longForm; action() }

inline fun ubyteFlag(shortForm: Char, noinline action: FlagOptions<UByte>.() -> Unit = {}) =
  ubyteFlag { this.shortForm = shortForm; action() }

fun ubyteFlag(action: FlagOptions<UByte>.() -> Unit = {}): DelegateFlag<UByte> =
  BasicDelegateFlag.of(FlagOptions(UByte::class).also {
    it.formatter = NonNullGeneralStringifier.unsafeCast()
    it.action()
  })

// endregion UByte

// region UInt

inline fun uintFlag(longForm: String, noinline action: FlagOptions<UInt>.() -> Unit = {}) =
  uintFlag { this.longForm = longForm; action() }

inline fun uintFlag(shortForm: Char, noinline action: FlagOptions<UInt>.() -> Unit = {}) =
  uintFlag { this.shortForm = shortForm; action() }

fun uintFlag(action: FlagOptions<UInt>.() -> Unit = {}): DelegateFlag<UInt> =
  BasicDelegateFlag.of(FlagOptions(UInt::class).also {
    it.formatter = NonNullGeneralStringifier.unsafeCast()
    it.action()
  })

// endregion UInt

// region ULong

inline fun ulongFlag(longForm: String, noinline action: FlagOptions<ULong>.() -> Unit = {}) =
  ulongFlag { this.longForm = longForm; action() }

inline fun ulongFlag(shortForm: Char, noinline action: FlagOptions<ULong>.() -> Unit = {}) =
  ulongFlag { this.shortForm = shortForm; action() }

fun ulongFlag(action: FlagOptions<ULong>.() -> Unit = {}): DelegateFlag<ULong> =
  BasicDelegateFlag.of(FlagOptions(ULong::class).also {
    it.formatter = NonNullGeneralStringifier.unsafeCast()
    it.action()
  })

// endregion ULong

// region UShort

inline fun ushortFlag(longForm: String, noinline action: FlagOptions<UShort>.() -> Unit = {}) =
  ushortFlag { this.longForm = longForm; action() }

inline fun ushortFlag(shortForm: Char, noinline action: FlagOptions<UShort>.() -> Unit = {}) =
  ushortFlag { this.shortForm = shortForm; action() }

fun ushortFlag(action: FlagOptions<UShort>.() -> Unit = {}): DelegateFlag<UShort> =
  BasicDelegateFlag.of(FlagOptions(UShort::class).also {
    it.formatter = NonNullGeneralStringifier.unsafeCast()
    it.action()
  })

// endregion UShort
