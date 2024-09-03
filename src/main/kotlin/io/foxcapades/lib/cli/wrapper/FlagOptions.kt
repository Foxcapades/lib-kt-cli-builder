package io.foxcapades.lib.cli.wrapper

import kotlin.reflect.KClass

open class FlagOptions<T: Any>(type: KClass<out T>) : BaseFlagOptions<T, T>(type)
