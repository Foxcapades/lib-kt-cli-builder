package io.foxcapades.lib.cli.builder.util

open class NoSuchValueException(msg: String) : RuntimeException(msg) {
  constructor() : this("attempted to unwrap a value that had not been set")
}
