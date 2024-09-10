package io.foxcapades.lib.cli.builder.arg.ref.impl

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.arg.impl.CliArgumentAnnotationImpl
import io.foxcapades.lib.cli.builder.arg.ref.ResolvedArgumentValueRef
import io.foxcapades.lib.cli.builder.command.ref.ResolvedCommand
import kotlin.reflect.KCallable

internal class AnnotatedValueArgument<T : Any, V>(
  annotation: CliArgumentAnnotationImpl,
  parent:     ResolvedCommand<T>,
  instance:   Argument<V>,
  accessor:   KCallable<Argument<V>>,
)
  : ResolvedArgumentValueRef<T, V>
  , BaseAnnotatedValueArgument<ResolvedCommand<T>, V>(annotation, parent, instance)
{
  override val accessor = accessor

  override val containingType
    get() = parentComponent.type

  override val qualifiedName: String
    get() = super<ResolvedArgumentValueRef>.qualifiedName
}
