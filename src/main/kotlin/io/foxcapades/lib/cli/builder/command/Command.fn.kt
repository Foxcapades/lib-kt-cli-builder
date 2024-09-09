package io.foxcapades.lib.cli.builder.command

/**
 * Named type over a simple [Pair] use case.
 *
 * @since 1.0.0
 */
typealias CliCommandComponents = Pair<String, Iterable<Any>>

/**
 * Wraps the given values in a new [CliCommandComponents] instance.
 *
 * @param name Command name.
 *
 * @param components Additional CLI call components such as flags, arguments,
 * sub commands, etc...
 *
 * @return a new [CliCommandComponents] instance wrapping the given values.
 */
@Suppress("NOTHING_TO_INLINE")
inline fun Command.componentsOf(name: String, vararg components: Any) =
  CliCommandComponents(name, components.asIterable())

