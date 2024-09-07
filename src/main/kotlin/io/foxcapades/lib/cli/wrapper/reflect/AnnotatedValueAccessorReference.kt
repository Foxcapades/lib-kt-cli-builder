package io.foxcapades.lib.cli.wrapper.reflect

import io.foxcapades.lib.cli.wrapper.meta.CliComponentAnnotation
import kotlin.reflect.KCallable

interface AnnotatedValueAccessorReference<T : Any, V, C : KCallable<V>, A : CliComponentAnnotation>
  : ValueAccessorReference<T, V, C>
{
  val annotation: A
}
