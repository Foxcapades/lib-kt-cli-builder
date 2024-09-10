package io.foxcapades.lib.cli.builder.arg.ref

import io.foxcapades.lib.cli.builder.arg.CliArgumentAnnotation
import io.foxcapades.lib.cli.builder.util.reflect.AnnotatedValueAccessorReference
import kotlin.reflect.KCallable

interface ResolvedImaginaryArgument<T : Any, V>
  : ResolvedArgument<V>
  , AnnotatedValueAccessorReference<T, V, KCallable<V>, CliArgumentAnnotation>
{
  override val qualifiedName: String
    get() = "argument " + super.qualifiedName
}
