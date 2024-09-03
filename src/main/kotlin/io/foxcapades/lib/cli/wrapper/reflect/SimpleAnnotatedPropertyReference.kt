package io.foxcapades.lib.cli.wrapper.reflect

import io.foxcapades.lib.cli.wrapper.meta.CliComponentAnnotation

internal data class SimpleAnnotatedPropertyReference<T : Any, V, A : CliComponentAnnotation>(
  private val ref: PropertyReference<T, V>,
  override val annotation: A
) : AnnotatedPropertyReference<T, V, A>, PropertyReference<T, V> by ref
