package io.foxcapades.lib.cli.builder

import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.serial.CommandSerializer
import io.foxcapades.lib.cli.builder.serial.impl.*

/**
 * CLI Serialization Helpers
 *
 * Provides basic methods which should cover most typical CLI call serialization
 * use cases.
 *
 * @since 1.0.0
 */
object Cli {
  /**
   * Attempts to serialize the given object into a CLI call string.
   *
   * @param cliCommand CLI call config to serialize.
   *
   * @param options Serialization options.
   *
   * @return The serialized CLI call string.
   */
  @JvmStatic
  @JvmOverloads
  fun toCliString(cliCommand: Any, options: CliSerializationConfig = CliSerializationConfig()) =
    newCommandSerializer(options).serializeToString(cliCommand)

  /**
   * Attempts to serialize the given object into an array of CLI call params
   * suitable for use with APIs such as [ProcessBuilder].
   *
   * When serializing into a collection of CLI call params, string quoting rules
   * do not apply.
   *
   * @param cliCommand CLI call config to serialize.
   *
   * @param options Serialization options.
   *
   * @return An array of strings representing the serialized CLI call.
   */
  @JvmStatic
  @JvmOverloads
  fun toCliCallArray(cliCommand: Any, options: CliSerializationConfig = CliSerializationConfig()) =
    newCommandSerializer(options).serializeToArray(cliCommand)

  /**
   * Attempts to serialize the given object into a list of CLI call params
   * suitable for use with APIs such as [ProcessBuilder].
   *
   * When serializing into a collection of CLI call params, string quoting rules
   * do not apply.
   *
   * @param cliCommand CLI call config to serialize.
   *
   * @param options Serialization options.
   *
   * @return A list of strings representing the serialized CLI call.
   */
  @JvmStatic
  @JvmOverloads
  fun toCliCallList(cliCommand: Any, options: CliSerializationConfig = CliSerializationConfig()) =
    newCommandSerializer(options).serializeToList(cliCommand)

  /**
   * Attempts to serialize the given object into CLI call params that are then
   * fed into a new [ProcessBuilder] instance.
   *
   * @param cliCommand CLI call config to serialize.
   *
   * @param options Serialization options.
   *
   * @return A new [ProcessBuilder] instance configured by the CLI call
   * serialized from the given input object.
   */
  @JvmStatic
  @JvmOverloads
  fun toProcessBuilder(cliCommand: Any, options: CliSerializationConfig = CliSerializationConfig()) =
    ProcessBuilder(toCliCallList(cliCommand, options))

  @JvmStatic
  @JvmOverloads
  fun newCommandSerializer(options: CliSerializationConfig = CliSerializationConfig()): CommandSerializer =
    CommandSerializerImpl(options)
}


