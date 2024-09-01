package io.foxcapades.lib.cli.wrapper.utils

import kotlin.reflect.KClass

internal inline val KClass<*>.safeName
  get() = qualifiedName ?: simpleName ?: "unknown"