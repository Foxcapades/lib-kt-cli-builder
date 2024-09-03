package io.foxcapades.lib.cli.wrapper.reflect

import io.foxcapades.lib.cli.wrapper.util.safeName
import kotlin.reflect.KClass

interface TypeReference<T : Any> {
  val type: KClass<out T>

  val qualifiedName: String get() = type.safeName
}

