package io.foxcapades.lib.cli.builder.flag.ref

import io.foxcapades.lib.cli.builder.command.ref.ResolvedCommand
import io.foxcapades.lib.cli.builder.flag.CliFlagAnnotation
import io.foxcapades.lib.cli.builder.util.reflect.AnnotatedValueAccessorReference
import kotlin.reflect.KCallable
import kotlin.reflect.KClass

interface ResolvedImaginaryFlag<T : Any, V>
  : ResolvedFlag<V>
  , AnnotatedValueAccessorReference<T, V, KCallable<V>, CliFlagAnnotation>
{
  override val parentComponent: ResolvedCommand<T>

  override val containingType: KClass<out T>
    get() = parentComponent.type

  override val qualifiedName
    get() = "flag " + super.qualifiedName
}
