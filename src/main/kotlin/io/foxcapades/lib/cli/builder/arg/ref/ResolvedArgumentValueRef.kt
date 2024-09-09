package io.foxcapades.lib.cli.builder.arg.ref

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.util.reflect.ValueAccessorReference
import kotlin.reflect.KCallable

interface ResolvedArgumentValueRef<T : Any, V>
  : ResolvedArgument<V>
  , ValueAccessorReference<T, Argument<V>, KCallable<Argument<V>>>
