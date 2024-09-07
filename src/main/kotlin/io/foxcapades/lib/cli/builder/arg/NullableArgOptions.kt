package io.foxcapades.lib.cli.builder.arg

import kotlin.reflect.KClass

open class NullableArgOptions<T : Any>(type: KClass<out T>) : BaseArgOptions<T, T?>(type)
