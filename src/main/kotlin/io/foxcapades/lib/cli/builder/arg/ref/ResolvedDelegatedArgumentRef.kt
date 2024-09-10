package io.foxcapades.lib.cli.builder.arg.ref

import io.foxcapades.lib.cli.builder.command.ref.ResolvedCommand
import io.foxcapades.lib.cli.builder.util.reflect.ValueAccessorReference
import kotlin.reflect.KCallable
import kotlin.reflect.KClass

interface ResolvedDelegatedArgumentRef<T : Any, V> : ResolvedArgument<V>, ValueAccessorReference<T, V, KCallable<V>> {
  override val parentComponent: ResolvedCommand<T>

  override val containingType: KClass<out T>
    get() = parentComponent.type

  override val qualifiedName: String
    get() = "argument " + super.qualifiedName
}
