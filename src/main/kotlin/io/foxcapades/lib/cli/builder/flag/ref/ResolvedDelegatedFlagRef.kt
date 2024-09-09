package io.foxcapades.lib.cli.builder.flag.ref

import io.foxcapades.lib.cli.builder.command.ResolvedCommand
import io.foxcapades.lib.cli.builder.util.reflect.ValueAccessorReference
import kotlin.reflect.KCallable

interface ResolvedDelegatedFlagRef<T : Any, V> : ResolvedFlag<V>, ValueAccessorReference<T, V, KCallable<V>> {
  override val parentComponent: ResolvedCommand<T>

  override val qualifiedName: String
    get() = "flag " + super.qualifiedName
}
