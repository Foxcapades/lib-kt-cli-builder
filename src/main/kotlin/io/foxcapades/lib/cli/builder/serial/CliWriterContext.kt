package io.foxcapades.lib.cli.builder.serial

import io.foxcapades.lib.cli.builder.util.reflect.ValueAccessorReference
import kotlin.reflect.KCallable

interface CliWriterContext<T : Any, V> {
  val config: CliSerializationConfig

  val reference: ValueAccessorReference<T, V, KCallable<V>>
}
