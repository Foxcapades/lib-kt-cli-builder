package io.foxcapades.lib.cli.wrapper.serial.values

import io.foxcapades.lib.cli.wrapper.CliCallComponent
import io.foxcapades.lib.cli.wrapper.ResolvedArgument
import io.foxcapades.lib.cli.wrapper.ResolvedFlag
import io.foxcapades.lib.cli.wrapper.reflect.PropertyReference
import io.foxcapades.lib.cli.wrapper.serial.CliSerializationConfig


/**
 * @param V Value type.
 *
 * @param P Resolved parent component type.
 */
fun interface ComponentDefaultTest<V, P> where P : CliCallComponent, P : PropertyReference<*, V> {
  fun valueIsDefault(value: V, reference: P, config: CliSerializationConfig): Boolean
}

/**
 * @param V Argument value type.
 */
fun interface ArgumentDefaultTest<V> : ComponentDefaultTest<V, ResolvedArgument<*, V>>

/**
 * @param V Flag argument value type.
 */
fun interface FlagDefaultTest<V> : ComponentDefaultTest<V, ResolvedFlag<*, V>>
