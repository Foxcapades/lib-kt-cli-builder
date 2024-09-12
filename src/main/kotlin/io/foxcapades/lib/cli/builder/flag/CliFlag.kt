package io.foxcapades.lib.cli.builder.flag

import io.foxcapades.lib.cli.builder.arg.CliArgument
import io.foxcapades.lib.cli.builder.flag.filter.FlagPredicate
import io.foxcapades.lib.cli.builder.flag.filter.UnconfiguredFlagFilter
import io.foxcapades.lib.cli.builder.flag.properties.PropertyNameFormatter
import kotlin.reflect.KClass

/**
 * Marks a property as one to be included in CLI call serialization and provides
 * options to configure how the property is handled.
 *
 * Example:
 * ```kt
 * @CliCommand("my-command")
 * data class MyType(
 *   @CliFlag(longForm = "input", shortForm = 'i', required = true)
 *   var inputPath: String? = null,
 *
 *   @CliFlag(longForm = "output", shortForm = 'o')
 *   var outputPath: String? = null,
 *
 *   @CliFlag(formatter = MyEnumFormatter::class)
 *   var someEnum: MyEnum? = null,
 * )
 *
 * val foo = MyType()
 * Cli.toCliString(foo) // Validation error, --input is required but not set
 *
 * foo.inputPath = "hello"
 * Cli.toCliString(foo) // my-command --input="hello"
 *
 * foo.outputPath = "goodbye"
 * Cli.toCliString(foo) // my-command --input="hello" --output="goodbye"
 *
 * foo.someEnum = MyEnum.SomeOption
 * Cli.toCliString(foo) // my-command --input="hello" --output="goodbye" --some-enum="some-option"
 * ```
 *
 * @see [CliFlag.required]
 */
@Target(AnnotationTarget.PROPERTY, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class CliFlag(
  /**
   * Sets a long-form name for the flag.
   *
   * If neither a long-form nor short-form flag name are provided, the value
   * will be fetched from the [delegate][Flag], if possible, otherwise will be
   * derived from the annotated property's name (see [PropertyNameFormatter]).
   */
  val longForm: String = "",

  /**
   * Sets a short-form name for the flag.
   *
   * If neither a long-form nor short-form flag name are provided, the value
   * will be fetched from the [delegate][Flag], if possible, otherwise a
   * long-form name will be derived from the annotated property's name (see
   * [PropertyNameFormatter]).
   */
  val shortForm: Char = '\u0000',

  val required: Toggle = Toggle.Unset,

  val inclusionTest: KClass<out FlagPredicate<*>> = UnconfiguredFlagFilter::class,

  val argument: CliArgument = CliArgument()
) {
  enum class Toggle { Yes, No, Unset }
}
