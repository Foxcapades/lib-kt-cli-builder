package io.foxcapades.lib.cli.builder.util.values

interface ValueAccessor<V> : ValueSource {
  operator fun invoke(): V
}
