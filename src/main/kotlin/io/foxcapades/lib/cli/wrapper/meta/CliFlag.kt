package io.foxcapades.lib.cli.wrapper.meta

import io.foxcapades.lib.cli.wrapper.Flag
import io.foxcapades.lib.cli.wrapper.serial.NullableGeneralStringifier
import io.foxcapades.lib.cli.wrapper.serial.properties.PropertyNameFormatter
import io.foxcapades.lib.cli.wrapper.serial.values.DefaultTestReqDependent
import io.foxcapades.lib.cli.wrapper.serial.values.DefaultValueTest
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter
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
 * @see [CliFlag.isRequired]
 */
@Target(AnnotationTarget.PROPERTY)
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

  /**
   * Whether this CLI flag should be considered required.
   *
   * ## Required Flags
   *
   * When [isRequired] is set to `true`, the following applies:
   *
   * 1. [includeDefault] is ignored
   * 2. [defaultValueTest] is used to indicate whether a value has been set,
   *    meaning property values for which `defaultValueTest` returns true will
   *    be considered as "unset" and result in a validation error.
   * 3. The default [defaultValueTest] implementation will follow these rules:
   *     a. Nullable fields will be tested with [DefaultValueTests.Null].
   *     b. Non-nullable fields will be treated with the rules outlined by
   *        [DefaultValueTests.Default].
   *
   * *Example: Standard Test - Nullable Field* (see [DefaultValueTests.Null])
   * ```kt
   * @CliCommand("my-command")
   * data class Command(
   *   @CliFlag(isRequired = true)
   *   var myFlag: String? = null
   * )
   *
   * val com = Command()
   * Cli.toCliString(com) // Validation error, `--my-flag` is required but not set
   *
   * com.myFlag = "hello"
   * Cli.toCliString(com) // my-command --my-flag="hello"
   * ```
   *
   * *Example: Standard Test - Non-Nullable Field* (see [DefaultValueTests.Default])
   * ```kt
   * @CliCommand("my-command")
   * data class Command(
   *   @CliFlag(isRequired = true)
   *   var myFlag: Double = 0.0
   * )
   *
   * val com = Command()
   * Cli.toCliString(com) // Validation error, `--my-flag` is required but not set
   *
   * com.myFlag = 1.0
   * Cli.toCliString(com) // my-command --my-flag=1.0
   * ```
   *
   * *Example: Customized Test*
   * ```kt
   * class MyTest : DefaultValueTest {
   *   fun testValue(value: Any?, annotation: CliFlag, prop: KProperty1<*, *>) =
   *     value == 42
   * }
   *
   * @CliCommand("my-command")
   * data class Command(
   *   @CliFlag(isRequired = true, defaultValueTest = MyTest::class)
   *   var myFlag: Int = 42
   * )
   *
   * val com = Command()
   * Cli.toCliString(com) // Validation error, `--my-flag` is required but not set
   *
   * com.myFlag = 69
   * Cli.toCliString(com) // my-command --my-flag=69
   * ```
   *
   * ## Optional Flags
   *
   * When [isRequired] is set to `false`, the following applies:
   *
   * 1. [defaultValueTest] is used to indicate whether a value is considered as
   *    being its default value.
   * 2. The default [defaultValueTest] implementation will follow the rules
   *    outlined by [DefaultValueTests.Default].

   * *Example: Standard Test* (see [DefaultValueTests.Default])
   * ```kt
   * @CliCommand("my-command")
   * data class Command(
   *   @CliFlag
   *   var myFlag: String? = null
   * )
   *
   * val com = Command()
   * Cli.toCliString(com) // my-command
   *
   * com.myFlag = ""
   * Cli.toCliString(com) // my-command
   *
   * com.myFlag = "hello"
   * Cli.toCliString(com) // my-command --my-flag="hello"
   * ```
   *
   * *Example: Customized Test*
   * ```kt
   * class MyTest : DefaultValueTest {
   *   fun testValue(value: Any?, annotation: CliFlag, prop: KProperty1<*, *>) =
   *     value == 42
   * }
   *
   * @CliCommand("my-command")
   * data class Command(
   *   @CliFlag(defaultValueTest = MyTest::class)
   *   var myFlag: Int = 42
   * )
   *
   * val com = Command()
   * Cli.toCliString(com) // my-command
   *
   * com.myFlag = 69
   * Cli.toCliString(com) // my-command --my-flag=69
   * ```
   */
  val isRequired: Boolean = false,

  /**
   * Used to test the property value to determine if it should be considered as
   * set to its "default" value.
   *
   * * When [isRequired] is set to `true`, a test result of `true` (is default)
   * indicates that the value has not been set, which would result in a
   * validation error.  A test result value of `false` (not default) indicates
   * that the value has been set.
   *
   * * When [isRequired] is set to `false`, a test result of `true` (is default)
   * indicates that the value may be omitted from the generated CLI call based
   * on the value of [includeDefault].  A test result value of `false` (not
   * default) indicates that the value has been changed and must be included
   * in the CLI call.
   */
  val defaultValueTest: KClass<out DefaultValueTest> = DefaultTestReqDependent::class,

  /**
   * Configures whether optional flags that are set to their default value
   * should be included in generated CLI calls.
   */
  val includeDefault: Boolean = false,

  /**
   * Sets a custom formatter to use to convert the property value to a string.
   *
   * This will only be used or instantiated if the flag is printed.
   */
  val formatter: KClass<out ValueFormatter<*>> = NullableGeneralStringifier::class,
)
