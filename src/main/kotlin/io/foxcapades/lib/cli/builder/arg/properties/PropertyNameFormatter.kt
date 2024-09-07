package io.foxcapades.lib.cli.builder.arg.properties

import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig

/**
 * Defines a type or function that may be used to format a class property name
 * into a CLI flag long-form name.
 *
 * @since 1.0.0
 * @author Elizabeth Paige Harper [https://github.com/foxcapades]
 */
fun interface PropertyNameFormatter {
  /**
   * Perform the name formatting on the given string, using options from the
   * given config.
   *
   * @param name Property name to format.
   *
   * @param serializationConfig CLI call string serialization configuration.
   *
   * @return The formatted property name.
   */
  fun format(name: String, serializationConfig: CliSerializationConfig): String

  companion object {
    /**
     * Formats class property names into a camel case string.
     *
     * ```kt
     * someDbName   -> "someDbName"
     * someDBName   -> "someDbName"
     * some_db_name -> "someDbName"
     * SOME_DB_NAME -> "someDbName"
     * SomeDbName   -> "someDbName"
     * ```
     */
    @JvmStatic
    val CamelCase: PropertyNameFormatter by lazy { CamelCasePropertyNameFormatter() }

    /**
     * Formats class property names into a pascal case string.
     *
     * ```kt
     * someDbName   -> "SomeDbName"
     * someDBName   -> "SomeDbName"
     * some_db_name -> "SomeDbName"
     * SOME_DB_NAME -> "SomeDbName"
     * SomeDbName   -> "SomeDbName"
     * ```
     */
    @JvmStatic
    val PascalCase: PropertyNameFormatter by lazy { PascalCasePropertyNameFormatter() }

    /**
     * Formats class property names into a macro case string.
     *
     * ```kt
     * someDbName   -> "SOME_DB_NAME"
     * someDBName   -> "SOME_DB_NAME"
     * some_db_name -> "SOME_DB_NAME"
     * SOME_DB_NAME -> "SOME_DB_NAME"
     * SomeDbName   -> "SOME_DB_NAME"
     * ```
     */
    @JvmStatic
    val MacroCase: PropertyNameFormatter by lazy { UpperSnakeCasePropertyNameFormatter() }

    /**
     * Formats class property names into a snake-case string.
     *
     * ```kt
     * someDbName   -> "some_db_name"
     * someDBName   -> "some_db_name"
     * some_db_name -> "some_db_name"
     * SOME_DB_NAME -> "some_db_name"
     * SomeDbName   -> "some_db_name"
     * ```
     */
    @JvmStatic
    val SnakeCase: PropertyNameFormatter by lazy { LowerSnakeCasePropertyNameFormatter() }

    /**
     * Formats class property names into an upper-snake-case string.
     *
     * ```kt
     * someDbName   -> "SOME_DB_NAME"
     * someDBName   -> "SOME_DB_NAME"
     * some_db_name -> "SOME_DB_NAME"
     * SOME_DB_NAME -> "SOME_DB_NAME"
     * SomeDbName   -> "SOME_DB_NAME"
     * ```
     */
    @JvmStatic
    inline val UpperSnakeCase get() = MacroCase

    /**
     * Formats class property names into a title-snake-case string.
     *
     * ```kt
     * someDbName   -> "Some_Db_Name"
     * someDBName   -> "Some_Db_Name"
     * some_db_name -> "Some_Db_Name"
     * SOME_DB_NAME -> "Some_Db_Name"
     * SomeDbName   -> "Some_Db_Name"
     * ```
     */
    @JvmStatic
    val TitleSnakeCase: PropertyNameFormatter by lazy { TitleSnakeCasePropertyNameFormatter() }

    /**
     * Formats class property names into a flat-case string.
     *
     * ```kt
     * someDbName   -> "somedbname"
     * someDBName   -> "somedbname"
     * some_db_name -> "somedbname"
     * SOME_DB_NAME -> "somedbname"
     * SomeDbName   -> "somedbname"
     * ```
     */
    @JvmStatic
    val FlatCase: PropertyNameFormatter by lazy { LowerFlatCasePropertyNameFormatter() }

    /**
     * Formats class property names into an upper-flat-case string.
     *
     * ```kt
     * someDbName   -> "SOMEDBNAME"
     * someDBName   -> "SOMEDBNAME"
     * some_db_name -> "SOMEDBNAME"
     * SOME_DB_NAME -> "SOMEDBNAME"
     * SomeDbName   -> "SOMEDBNAME"
     * ```
     */
    @JvmStatic
    val UpperFlatCase: PropertyNameFormatter by lazy { UpperFlatCasePropertyNameFormatter() }

    /**
     * Formats class property names into a kebab-case string.
     *
     * ```kt
     * someDbName   -> "some-db-name"
     * someDBName   -> "some-db-name"
     * some_db_name -> "some-db-name"
     * SOME_DB_NAME -> "some-db-name"
     * SomeDbName   -> "some-db-name"
     * ```
     */
    @JvmStatic
    val KebabCase: PropertyNameFormatter by lazy { LowerKebabCasePropertyNameFormatter() }

    /**
     * Formats class property names into an upper-kebab-case string.
     *
     * ```kt
     * someDbName   -> "SOME-DB-NAME"
     * someDBName   -> "SOME-DB-NAME"
     * some_db_name -> "SOME-DB-NAME"
     * SOME_DB_NAME -> "SOME-DB-NAME"
     * SomeDbName   -> "SOME-DB-NAME"
     * ```
     */
    @JvmStatic
    val UpperKebabCase: PropertyNameFormatter by lazy { UpperKebabCasePropertyNameFormatter() }

    /**
     * Formats class property names into a title-kebab-case string.
     *
     * ```kt
     * someDbName   -> "Some-Db-Name"
     * someDBName   -> "Some-Db-Name"
     * some_db_name -> "Some-Db-Name"
     * SOME_DB_NAME -> "Some-Db-Name"
     * SomeDbName   -> "Some-Db-Name"
     * ```
     */
    @JvmStatic
    val TitleKebabCase: PropertyNameFormatter by lazy { TitleKebabCasePropertyNameFormatter() }

    /**
     * Formats class property names into a train-case string.
     *
     * ```kt
     * someDbName   -> "Some-Db-Name"
     * someDBName   -> "Some-Db-Name"
     * some_db_name -> "Some-Db-Name"
     * SOME_DB_NAME -> "Some-Db-Name"
     * SomeDbName   -> "Some-Db-Name"
     * ```
     */
    @JvmStatic
    inline val TrainCase get() = TitleKebabCase
  }
}

