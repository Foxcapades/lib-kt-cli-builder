package io.foxcapades.lib.cli.builder.arg.format

/**
 * Creates a new [ArgumentFormatter] instance from the given simple formatter
 * function.
 *
 * The given function is expected to take the target value and return its string
 * representation.
 *
 * @param fn Stringifier function.
 *
 * @return A new `ArgumentFormatter` wrapping the given function.
 */
inline fun <T> ArgumentFormatter(crossinline fn: (T) -> String) =
  ArgumentFormatter { v, b, _ -> b.writeString(fn(v)) }


//


@Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
internal inline fun <T> ArgumentFormatter<*>.unsafeCast() =
  this as ArgumentFormatter<T>
