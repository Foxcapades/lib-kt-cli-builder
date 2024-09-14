@file:Suppress("NOTHING_TO_INLINE")
package io.foxcapades.lib.cli.builder.util

internal inline fun <T> Iterator<T>.filter(crossinline fn: (T) -> Boolean) = iterator {
  while (hasNext()) {
    val value = next()
    if (fn(value))
      yield(value)
  }
}

internal inline fun <T> Iterator<T>.toList(preSize: Int = 8): List<T> =
  ArrayList<T>(preSize).also { forEach(it::add) }
