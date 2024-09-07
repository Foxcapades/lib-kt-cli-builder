package io.foxcapades.lib.cli.builder.arg.format

import kotlin.reflect.KFunction1

@Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
internal inline fun ArgumentFormatter<*>.forceAny() =
  this as ArgumentFormatter<Any?>

@Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
internal inline fun <T> ArgumentFormatter<*>.unsafeCast() =
  this as ArgumentFormatter<T>

/**
 * Helper used to type a given function reference as a [ArgumentFormatter]
 * instance.
 *
 * ```kt
 * ValueFormatter(BigDecimal::toPlainString)
 * ```
 *
 * @param fn Function reference to wrap.
 *
 * @return The function wrapped as a [ArgumentFormatter].
 */
@Suppress("NOTHING_TO_INLINE")
inline fun <T> ArgumentFormatter(fn: KFunction1<T, String>) =
  ArgumentFormatter { v, b -> b.writeString(fn(v)) }

inline fun <T> ArgumentFormatter(crossinline fn: (T) -> String) =
  ArgumentFormatter { v, b -> b.writeString(fn(v)) }
