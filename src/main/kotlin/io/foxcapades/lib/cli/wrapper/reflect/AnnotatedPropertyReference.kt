package io.foxcapades.lib.cli.wrapper.reflect

import io.foxcapades.lib.cli.wrapper.meta.CliComponentAnnotation

interface AnnotatedPropertyReference<T : Any, V, A : CliComponentAnnotation> : PropertyReference<T, V> {
  val annotation: A
}
