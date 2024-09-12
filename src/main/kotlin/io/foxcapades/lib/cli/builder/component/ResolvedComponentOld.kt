package io.foxcapades.lib.cli.builder.component

/**
 * Represents a component that has been resolved to a single instance and
 * class property.
 *
 * @param T Component container class type.
 *
 * @param V Component value type.
 *
 * @since 1.0.0
 */
interface ResolvedComponentOld<T : Any, V> : CliCallComponent {
  val instance: T
}
