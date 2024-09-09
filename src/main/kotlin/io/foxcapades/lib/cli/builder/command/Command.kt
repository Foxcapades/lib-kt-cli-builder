package io.foxcapades.lib.cli.builder.command

import io.foxcapades.lib.cli.builder.arg.filter.ArgumentPredicate
import io.foxcapades.lib.cli.builder.component.CliCallComponent
import io.foxcapades.lib.cli.builder.flag.filter.FlagPredicate
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.command.CliCommand as ComAnn

/**
 * # CLI Command Definition
 *
 * Defines a type that may provide more explicit control over which
 * subcomponents are serialized and in what order.
 *
 * Implementing types will be entirely represented by the subcomponents or
 * values provided via the [getCliCallComponents] method.
 *
 * Simple Implementation Example
 * ```kt
 * /**
 *  * Generates the command: greet --name="[foo]" --greeting="[bar]"
 *  */
 * class MyCommand : CliCommand {
 *   val foo by stringFlag("name")
 *
 *   val bar by stringFlag("greeting")
 *
 *   override fun getCliCallComponents(config: CliSerializationConfig): CliCommandComponents {
 *     return componentsOf("greet", this::foo, this::bar)
 *   }
 * }
 *```
 *
 * @since 1.0.0
 */
interface Command : CliCallComponent {

  /**
   * Returns an [Iterable] type which may be used to retrieve and ordered series
   * of CLI call components.
   *
   * ### Output Control
   *
   * Only items made available through this method will be considered for
   * serialization.  This means implementations must ensure that required
   * elements are included in the `Iterable` for validation of those elements to
   * occur.
   *
   * The inclusion of an element in the returned `Iterable` does not guarantee
   * its presence in the output CLI call, standard inclusion rules and filters
   * set via [FlagPredicates][FlagPredicate] or
   * [ArgumentPredicates][ArgumentPredicate] are still applied.
   *
   * ### Component Types
   *
   * Items in the returned `Iterable` are expected to be one of the following
   * types:
   *
   * * Instance property reference
   * * Instance getter method reference
   * * [Command] instance (for sub commands)
   * * Annotated class instance (for containers, or sub-commands if the
   *   [@CliCommand][ComAnn] annotation is present)
   * * [CharSequence]
   * * Any primitive value (including Kotlin unsigned types)
   *
   * Items in the returned `Iterable` that are not one of the above types will
   * be serialized by calling [toString], wrapping the output value as a string
   * if necessary.
   *
   * @param config Configuration for the current serialization attempt.
   *
   * @return A [CliCommandComponents] instance consisting of this command's name
   * followed by an [Iterable] which may return zero or more additional CLI call
   * components.
   */
  fun getCliCallComponents(config: CliSerializationConfig): CliCommandComponents
}
