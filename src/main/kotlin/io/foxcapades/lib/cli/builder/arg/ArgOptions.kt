package io.foxcapades.lib.cli.builder.arg

import kotlin.reflect.KClass

open class ArgOptions<T : Any>(type: KClass<out T>) : BaseArgOptions<T, T>(type)
