package io.foxcapades.lib.cli.builder.util

internal inline fun <T> Iterator<T>.filter(crossinline fn: (T) -> Boolean) = iterator {
  while (hasNext()) {
    val value = next()
    if (fn(value))
      yield(value)
  }
}

internal inline fun <T> Iterator<T>.onEach(crossinline fn: (T) -> Unit) = iterator {
  while (hasNext()) {
    yield(next().apply(fn))
  }
}

internal inline fun <T, R> Iterator<T>.map(crossinline fn: (T) -> R) = iterator {
  while (hasNext()) {
    yield(next().let(fn))
  }
}

internal inline fun <T, R> Iterator<T>.mapNonNull(crossinline fn: (T) -> R?) = iterator {
  while (hasNext())
    next().let(fn)?.then { yield(it) }
}

internal inline fun <reified R : Any> Iterable<*>.findInstance(): R? =
  find { it is R }?.let { it as R }
