package io.foxcapades.lib.cli.wrapper

import kotlin.reflect.KClass

class ArgOptions<T : Any>(type: KClass<out T>) : BaseArgOptions<T, T>(type)
