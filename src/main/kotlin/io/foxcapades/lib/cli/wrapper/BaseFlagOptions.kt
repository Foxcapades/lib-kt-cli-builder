package io.foxcapades.lib.cli.wrapper

import io.foxcapades.lib.cli.wrapper.util.SimpleProperty
import kotlin.reflect.KClass

sealed class BaseFlagOptions<T : Any, O : T?>(type: KClass<out T>)
  : BaseComponentOptions<T, O, ResolvedFlag<*, O>>(type)
{
  var longForm: String by SimpleProperty()
  var shortForm: Char by SimpleProperty()
  var requireFlag: Boolean by SimpleProperty()
}
