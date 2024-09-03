package io.foxcapades.lib.cli.wrapper

import kotlin.reflect.KClass

sealed class BaseArgOptions<T : Any, O : T?>(type: KClass<out T>)
  : BaseComponentOptions<T, O, ResolvedArgument<*, O>>(type)
