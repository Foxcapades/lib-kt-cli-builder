package io.foxcapades.lib.cli.wrapper.reflect

import kotlin.reflect.KProperty1

interface PropertyReference<T : Any, V> : TypeReference<T> {
  val property: KProperty1<T, V>

  val propertyIsNullable
    get() = property.returnType.isMarkedNullable
}
