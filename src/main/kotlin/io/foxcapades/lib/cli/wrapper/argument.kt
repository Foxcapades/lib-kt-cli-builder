package io.foxcapades.lib.cli.wrapper

import io.foxcapades.lib.cli.wrapper.serial.CliArgumentAppender
import io.foxcapades.lib.cli.wrapper.serial.CliSerializationConfig
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentPredicate
import io.foxcapades.lib.cli.wrapper.util.MutableDefaultableProperty
import io.foxcapades.lib.cli.wrapper.util.MutableProperty
import kotlin.reflect.KClass

// region Argument Def

/**
 * Represents a single positional or flag argument.
 */
interface Argument<V> : MutableDefaultableProperty<V>, CliCallComponent {
  val isRequired: Boolean

  val shouldQuote: Boolean

  fun shouldSerialize(config: CliSerializationConfig, reference: ResolvedComponent<*, V>): Boolean

  fun writeToString(builder: CliArgumentAppender)

  @Throws(UnsetArgumentDefaultException::class)
  override fun getDefault(): V
}

inline val Argument<*>.isDefault
  get() = hasDefault && isSet && getDefault() == get()

// endregion Argument Def

/**
 * @param T Argument container class type.
 *
 * @param V Argument value type.
 */
interface ResolvedArgument<T : Any, V> : ResolvedComponent<T, V>, Argument<V>

abstract class BaseArgOptions<T : Any, O : T?>(type: KClass<out T>) : BaseComponentOptions<T>(type) {
  var required by MutableProperty<Boolean>()
  var default by MutableProperty<O>()
  var filter by MutableProperty<ArgumentPredicate<out Argument<O>, O>>()
  var shouldQuote by MutableProperty<Boolean>()
  var formatter by MutableProperty<ArgumentFormatter<O>>()
}

open class ArgOptions<T : Any>(type: KClass<out T>) : BaseArgOptions<T, T>(type)

open class NullableArgOptions<T : Any>(type: KClass<out T>) : BaseArgOptions<T, T?>(type)
