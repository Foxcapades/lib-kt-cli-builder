package io.foxcapades.lib.cli.builder.arg

import io.foxcapades.lib.cli.builder.arg.filter.ArgumentPredicate
import io.foxcapades.lib.cli.builder.arg.format.ArgumentFormatter
import io.foxcapades.lib.cli.builder.component.BaseComponentOptions
import io.foxcapades.lib.cli.builder.util.properties.MutableProperty
import kotlin.reflect.KClass

/**
 * Base options for arguments.
 *
 * @param T Concrete type of the value the argument will contain.
 *
 * @param O Actual, nullable type that the argument may contain.
 *
 * @since 1.0.0
 */
abstract class BaseArgOptions<T : Any, O : T?>(type: KClass<out T>) : BaseComponentOptions<T>(type) {

  /**
   * Whether this argument should be considered required.
   *
   * TODO: document what this means, especially in relation to flag option for
   *       required.
   */
  var required by MutableProperty<Boolean>()

  /**
   * Default value for this argument.
   */
  var default by MutableProperty<O>()

  /**
   * Argument inclusion predicate.
   *
   * Used to determine whether an argument should appear in generated CLI call
   * forms.
   *
   * If the argument is marked as required, this filter is not used.
   */
  var filter by MutableProperty<ArgumentPredicate<out Argument<O>, O>>()

  /**
   * Whether this argument's value should be quoted.
   *
   * Only relevant when building CLI call strings.
   *
   * Default value is dependent on type; built-in numeric types and boolean are
   * not quoted by default, everything else is.
   */
  var shouldQuote by MutableProperty<Boolean>()

  /**
   * Formater used to render the argument's value as a string.
   */
  var formatter by MutableProperty<ArgumentFormatter<O>>()
}
