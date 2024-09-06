package io.foxcapades.lib.cli.wrapper

import io.foxcapades.lib.cli.wrapper.reflect.PropertyReference
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
abstract class BaseComponentOptions<V : Any>(internal val type: KClass<out V>)
