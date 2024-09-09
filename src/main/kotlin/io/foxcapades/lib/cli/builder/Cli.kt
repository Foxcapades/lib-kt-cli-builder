package io.foxcapades.lib.cli.builder

import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.serial.CommandSerializer
import io.foxcapades.lib.cli.builder.serial.impl.CommandSerializerCore

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
   * @param cliConfig CLI call config to serialize.
   *
   * @param options Serialization options.
   *
   * @return The serialized CLI call string.
   */
  @JvmStatic
  @JvmOverloads
  fun <T : Any> toCliString(cliConfig: T, options: CliSerializationConfig = CliSerializationConfig()) =
    CommandSerializerCore(cliConfig, options).serializeToString()

  /**
   * Attempts to serialize the given object into an array of CLI call params
   * suitable for use with APIs such as [ProcessBuilder].
   *
   * When serializing into a collection of CLI call params, string quoting rules
   * do not apply.
   *
   * @param cliConfig CLI call config to serialize.
   *
   * @param options Serialization options.
   *
   * @return An array of strings representing the serialized CLI call.
   */
  @JvmStatic
  @JvmOverloads
  fun <T : Any> toCliCallArray(cliConfig: T, options: CliSerializationConfig = CliSerializationConfig()) =
    CommandSerializerCore(cliConfig, options).serializeToArray()

  /**
   * Attempts to serialize the given object into a list of CLI call params
   * suitable for use with APIs such as [ProcessBuilder].
   *
   * When serializing into a collection of CLI call params, string quoting rules
   * do not apply.
   *
   * @param cliConfig CLI call config to serialize.
   *
   * @param options Serialization options.
   *
   * @return A list of strings representing the serialized CLI call.
   */
  @JvmStatic
  @JvmOverloads
  fun <T : Any> toCliCallList(cliConfig: T, options: CliSerializationConfig = CliSerializationConfig()) =
    CommandSerializerCore(cliConfig, options).serializeToList()

  /**
   * Attempts to serialize the given object into CLI call params that are then
   * fed into a new [ProcessBuilder] instance.
   *
   * @param cliConfig CLI call config to serialize.
   *
   * @param options Serialization options.
   *
   * @return A new [ProcessBuilder] instance configured by the CLI call
   * serialized from the given input object.
   */
  @JvmStatic
  @JvmOverloads
  fun <T : Any> toProcessBuilder(cliConfig: T, options: CliSerializationConfig = CliSerializationConfig()) =
    ProcessBuilder(CommandSerializerCore(cliConfig, options).serializeToList())

  @JvmStatic
  @JvmOverloads
  fun <T : Any> newCommandSerializer(cliConfig: T, options: CliSerializationConfig = CliSerializationConfig()): CommandSerializer<T> =
    ProcessB
}


