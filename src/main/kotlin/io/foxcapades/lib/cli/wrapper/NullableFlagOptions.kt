package io.foxcapades.lib.cli.wrapper

import kotlin.reflect.KClass

class NullableFlagOptions<T: Any>(type: KClass<out T>) : BaseFlagOptions<T, T?>(type)
