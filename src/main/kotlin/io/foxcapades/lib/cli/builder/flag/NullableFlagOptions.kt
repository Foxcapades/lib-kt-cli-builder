package io.foxcapades.lib.cli.builder.flag

import io.foxcapades.lib.cli.builder.arg.NullableArgOptions
import kotlin.reflect.KClass


open class NullableFlagOptions<T: Any>(type: KClass<out T>)
  : BaseFlagOptions<T, T?, NullableArgOptions<T>>(type, NullableArgOptions(type))
