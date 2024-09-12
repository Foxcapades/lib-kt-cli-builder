package io.foxcapades.lib.cli.builder.flag.ref

import io.foxcapades.lib.cli.builder.command.ref.ResolvedCommand
import io.foxcapades.lib.cli.builder.flag.Flag

/**
 * Represents a reference to a [Flag] instance that is used as a property
 * delegate on its parent command config.
 *
 * @param T Parent/containing class type.
 *
 * @param V Flag argument value type.
 */
interface ResolvedBoundFlagRef<T : Any, V> : ResolvedFlag<V> {
  /**
   * Parent command definition class.
   */
  override val parentComponent: ResolvedCommand<T>
}
