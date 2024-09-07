package io.foxcapades.lib.cli.wrapper.serial

import io.foxcapades.lib.cli.wrapper.reflect.ValueAccessorReference
import kotlin.reflect.KCallable

interface CliWriterContext<T : Any, V> {
  val config: CliSerializationConfig

  val reference: ValueAccessorReference<T, V, out KCallable<V>>
}
