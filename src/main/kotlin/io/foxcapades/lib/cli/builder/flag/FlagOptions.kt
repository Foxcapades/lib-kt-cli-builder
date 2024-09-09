package io.foxcapades.lib.cli.builder.flag

import io.foxcapades.lib.cli.builder.arg.ArgOptions
import kotlin.reflect.KClass

open class FlagOptions<T : Any>(type: KClass<out T>)
  : BaseFlagOptions<T, T, ArgOptions<T>>(type, ArgOptions(type))
