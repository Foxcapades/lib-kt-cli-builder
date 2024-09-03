package io.foxcapades.lib.cli.wrapper.reflect

import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

internal data class PropertyReferenceImpl<T : Any, V>(
  override val type: KClass<out T>,
  override val property: KProperty1<T, V>,
) : PropertyReference<T, V>
