package io.foxcapades.lib.cli.builder.flag

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.flag.impl.BigDecimalFlagImpl
import io.foxcapades.lib.cli.builder.flag.impl.UniversalFlagImpl
import java.math.BigDecimal


@Suppress("NOTHING_TO_INLINE")
inline fun bigDecimalFlag(longForm: String, noinline action: FlagOptions<BigDecimal>.() -> Unit = {}) =
  bigDecimalFlag { this.longForm = longForm; action() }

@Suppress("NOTHING_TO_INLINE")
inline fun bigDecimalFlag(shortForm: Char, noinline action: FlagOptions<BigDecimal>.() -> Unit = {}) =
  bigDecimalFlag { this.shortForm = shortForm; action() }

fun bigDecimalFlag(action: FlagOptions<BigDecimal>.() -> Unit = {}): Flag<Argument<BigDecimal>, BigDecimal> =
  BigDecimalFlagImpl(FlagOptions(BigDecimal::class).also(action))

fun nullableBigDecimalFlag(
  action: NullableFlagOptions<BigDecimal>.() -> Unit = {}
): Flag<Argument<BigDecimal?>, BigDecimal?> =
  UniversalFlagImpl.of(NullableFlagOptions(BigDecimal::class).also(action))
