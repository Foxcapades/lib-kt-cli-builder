@file:Suppress("NOTHING_TO_INLINE")
package io.foxcapades.lib.cli.wrapper.serial.values

import kotlin.reflect.KFunction1

/**
 * Helper used to type a given function reference as a [ValueFormatter]
 * instance.
 *
 * ```kt
 * ValueFormatter(BigDecimal::toPlainString)
 * ```
 *
 * @param fn Function reference to wrap.
 *
 * @return The function wrapped as a [ValueFormatter].
 */
inline fun <T> ValueFormatter(fn: KFunction1<T, String>) = ValueFormatter.simple(fn)
