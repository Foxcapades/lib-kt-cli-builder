package io.foxcapades.lib.cli.builder.util.properties

/**
 * # No Such Default Value
 *
 * Exception thrown by [DefaultableProperty] types when an attempt is made to
 * access a default value that does not exist.
 *
 * @since 1.0.0
 */
open class NoSuchDefaultValueException(msg: String) : NoSuchValueException(msg) {
  constructor() : this("attempted to unwrap a default value that had not been set")
}
