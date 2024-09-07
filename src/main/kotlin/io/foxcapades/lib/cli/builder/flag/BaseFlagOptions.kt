package io.foxcapades.lib.cli.builder.flag

import io.foxcapades.lib.cli.builder.component.BaseComponentOptions
import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.arg.BaseArgOptions
import io.foxcapades.lib.cli.builder.arg.filter.ArgumentPredicate
import io.foxcapades.lib.cli.builder.flag.filter.FlagPredicate
import io.foxcapades.lib.cli.builder.util.properties.MutableProperty
import kotlin.reflect.KClass

open class BaseFlagOptions<V : Any, O : V?, A : BaseArgOptions<V, O>>(
  type: KClass<out V>,
  arg: A,
)
  : BaseComponentOptions<V>(type)
{
  /**
   * Sets the long-form name of the flag being configured.
   */
  var longForm by MutableProperty<String>()

  /**
   * Sets the short-form name of the flag being configured.
   */
  var shortForm by MutableProperty<Char>()

  /**
   * Sets whether this flag's presence is required in a CLI generated call.
   */
  var required by MutableProperty<Boolean>()

  /**
   * Defines a predicate which will is used to determine when a non-required
   * flag should be included or omitted from a CLI call.
   *
   * Flags marked as being [required] will always be included without any call
   * to this filter.
   */
  var flagFilter by MutableProperty<FlagPredicate<out Flag<Argument<O>, O>, out Argument<O>, O>>()

  /**
   * Argument configuration
   */
  val argument: A = arg

  /**
   * Argument configuration action.
   *
   * Calls the given action on the value of [argument].
   */
  inline fun argument(crossinline action: A.() -> Unit) = argument.action()

  /**
   * Convenience shortcut to set `argument.required`.
   *
   * @see [BaseArgOptions.required]
   */
  inline var argRequired: Boolean
    get() = argument.required
    set(value) { argument.required = value }

  /**
   * Convenience shortcut to set `argument.filter`
   *
   * @see [BaseArgOptions.filter]
   */
  inline var argFilter: ArgumentPredicate<out Argument<O>, O>
    get() = argument.filter
    set(value) { argument.filter = value }

  /**
   * Convenience shortcut to set `argument.default`
   *
   * @see [BaseArgOptions.default]
   */
  inline var default
    get() = argument.default
    set(value) { argument.default = value }

  /**
   * Convenience shortcut to set `argument.shouldQuote`
   *
   * @see [BaseArgOptions.shouldQuote]
   */
  inline var shouldQuote
    get() = argument.shouldQuote
    set(value) { argument.shouldQuote = value }

  /**
   * Convenience shortcut to set `argument.formatter`
   *
   * @see [BaseArgOptions.formatter]
   */
  inline var formatter
    get() = argument.formatter
    set(value) { argument.formatter = value }
}
