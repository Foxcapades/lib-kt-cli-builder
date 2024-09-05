package io.foxcapades.lib.cli.wrapper

import io.foxcapades.lib.cli.wrapper.reflect.PropertyReference
import io.foxcapades.lib.cli.wrapper.serial.CliSerializationConfig
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.serial.values.ComponentDefaultTest
import io.foxcapades.lib.cli.wrapper.util.SimpleProperty
import kotlin.reflect.KClass

/**
 * Base type from which all CLI components are derived.
 *
 * @since 1.0.0
 */
sealed interface CliCallComponent

/**
 * Represents a component that has been resolved to a single instance and
 * class property.
 *
 * @param T Component container class type.
 *
 * @param V Component value type.
 *
 * @since 1.0.0
 */
sealed interface ResolvedComponent<T : Any, V> : CliCallComponent, PropertyReference<T, V>

/**
 * Base type containing options common to all CLI components.
 *
 * @param V Safe, concrete value type.
 *
 * @param O Usable value type, extends [V], may be nullable.
 *
 * @param P Target resolved component type.
 *
 * @since 1.0.0
 */
abstract class BaseComponentOptions<V : Any, O : V?, P>(internal val type: KClass<out V>)
  where
    P : CliCallComponent
  , P : PropertyReference<*, O>
{
  /**
   * Test used to determine if a value has been set to a value that should be
   * considered the "default" value for the specific component instance.
   *
   * If unset, the fallback default test may be dependent on the specific
   * component or value type.
   */
  var defaultValueTest: ComponentDefaultTest<O, P> by SimpleProperty()

  /**
   * Default value for this component.
   *
   * TODO: what is the interplay between this and [defaultValueTest]?
   */
  var default: O by SimpleProperty()

  /**
   * Whether this value should be included in the rendered output if or when it
   * is set to a value considered to be the "default" value.
   *
   * If set, this takes priority over the value set in [CliSerializationConfig].
   */
  var includeDefault: Boolean by SimpleProperty()

  /**
   * Formatter instance used to render the wrapped value.
   *
   * If unset, the fallback formatter may be dependent on the specific component
   * or value type.
   */
  var formatter: ArgumentFormatter<O> by SimpleProperty()

  /**
   * If this component is an argument, determines if it is required to be
   * present in its parent container, else determines whether the argument
   * contained in this component is required.
   *
   * If unset, the fallback value may be dependent on the type or containing
   * parent.
   */
  var requireArg: Boolean by SimpleProperty()

  /**
   * Whether this value should be quoted when rendered.
   *
   * If unset, the fallback value is dependent on the specific component or
   * value type.
   */
  var shouldQuote: Boolean by SimpleProperty()
}
