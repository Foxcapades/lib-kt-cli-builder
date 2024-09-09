package io.foxcapades.lib.cli.builder.util.reflect

import io.foxcapades.lib.cli.builder.component.CliComponentAnnotation
import kotlin.reflect.KCallable

interface AnnotatedValueAccessorReference<T : Any, V, C : KCallable<V>, A : CliComponentAnnotation>
  : ValueAccessorReference<T, V, C>
{
  val annotation: A
}
