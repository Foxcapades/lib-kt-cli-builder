package io.foxcapades.lib.cli.wrapper

import io.foxcapades.lib.cli.wrapper.reflect.PropertyReference
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.serial.values.ComponentDefaultTest
import io.foxcapades.lib.cli.wrapper.util.SimpleProperty
import kotlin.reflect.KClass

/**
 * @param V Safe, concrete value type.
 *
 * @param O Usable value type, extends [V], may be nullable.
 *
 * @param P Target resolved component type.
 */
sealed class BaseComponentOptions<V : Any, O : V?, P>(internal val type: KClass<out V>) where P : CliCallComponent, P : PropertyReference<*, O> {
  var defaultValueTest: ComponentDefaultTest<O, P> by SimpleProperty()
  var default: O by SimpleProperty()
  var includeDefault: Boolean by SimpleProperty()
  var formatter: ArgumentFormatter<O> by SimpleProperty()
  var requireArg: Boolean by SimpleProperty()
  var shouldQuote: Boolean by SimpleProperty()
}
