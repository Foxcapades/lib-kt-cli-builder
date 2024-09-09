package io.foxcapades.lib.cli.builder.util.reflect

import kotlin.reflect.KClass

interface TypeReference<T : Any> {
  val type: KClass<out T>

  val qualifiedName: String get() = type.safeName
}

