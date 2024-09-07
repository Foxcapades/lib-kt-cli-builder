package io.foxcapades.lib.cli.builder.component

import kotlin.reflect.KClass

/**
 * Base type containing options common to all CLI components.
 *
 * @param V Actual value type.
 *
 * @since 1.0.0
 */
abstract class BaseComponentOptions<V : Any>(internal val type: KClass<out V>)
