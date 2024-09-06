package io.foxcapades.lib.cli.wrapper.serial.values

import io.foxcapades.lib.cli.wrapper.serial.CliArgumentAppender
import kotlin.reflect.KFunction1

/**
 * @param V Argument value type.
 */
fun interface ArgumentFormatter<V> {
  fun formatValue(value: V, builder: CliArgumentAppender)

  companion object {
    @JvmStatic
    fun <T: Any> ofToString() = ArgumentFormatter<T> { v, b -> b.appendString(v.toString()) }

    @JvmStatic
    fun <T> simple(fn: (T) -> String) = ArgumentFormatter<T> { v, b -> b.appendString(fn(v)) }
  }
}

@Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
internal inline fun ArgumentFormatter<*>.forceAny() =
  this as ArgumentFormatter<Any?>

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
inline fun <T> ArgumentFormatter(fn: KFunction1<T, String>) = ArgumentFormatter.simple(fn)
