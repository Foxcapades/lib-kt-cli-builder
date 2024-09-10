package io.foxcapades.lib.cli.builder.arg.ref.impl

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.arg.ref.ResolvedArgumentValueRef
import io.foxcapades.lib.cli.builder.component.ResolvedComponent
import kotlin.reflect.KCallable
import kotlin.reflect.KClass

internal class ValueArgument<T : Any, V>(
  containerType: KClass<out T>,
  parent:        ResolvedComponent,
  instance:      Argument<V>,
  accessor:      KCallable<Argument<V>>,
)
  : ResolvedArgumentValueRef<T, V>
  , AbstractValueArgument<T, V>(containerType, parent, instance, accessor)
