package io.foxcapades.lib.cli.builder

import io.foxcapades.lib.cli.builder.command.CliCommand
import io.foxcapades.lib.cli.builder.command.Command
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.serial.CommandSerializer
import io.foxcapades.lib.cli.builder.serial.impl.CommandSerializerImpl

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
   * The given object must either implement the [Command] interface, or be
   * annotated with [@CliCommand][CliCommand] to be serialized.  Providing a
   * value that does not meet at least one of those requirements will cause in
   * an exception to be thrown.
   *
   * @param cliCommand CLI call config to serialize.
   *
   * @param options Serialization options.
   *
   * @return The serialized CLI call string.
   *
   * @throws CliSerializationException If the given [cliCommand] value is not a
   * [Command] instance and is not annotated with [@CliCommand][CliCommand].
   */
  @JvmStatic
  @JvmOverloads
  @Throws(CliSerializationException::class)
  fun toCliString(cliCommand: Any, options: CliSerializationConfig = CliSerializationConfig()) =
    newCommandSerializer(options).serializeToString(cliCommand)

  /**
   * Attempts to serialize the given object into an array of CLI call params
   * suitable for use with APIs such as [ProcessBuilder].
   *
   * When serializing into a collection of CLI call params, string quoting rules
   * do not apply.
   *
   * The given object must either implement the [Command] interface, or be
   * annotated with [@CliCommand][CliCommand] to be serialized.  Providing a
   * value that does not meet at least one of those requirements will cause in
   * an exception to be thrown.
   *
   * @param cliCommand CLI call config to serialize.
   *
   * @param options Serialization options.
   *
   * @return An array of strings representing the serialized CLI call.
   *
   * @throws CliSerializationException If the given [cliCommand] value is not a
   * [Command] instance and is not annotated with [@CliCommand][CliCommand].
   */
  @JvmStatic
  @JvmOverloads
  @Throws(CliSerializationException::class)
  fun toCliCallArray(cliCommand: Any, options: CliSerializationConfig = CliSerializationConfig()) =
    newCommandSerializer(options).serializeToArray(cliCommand)

  /**
   * Attempts to serialize the given object into a list of CLI call params
   * suitable for use with APIs such as [ProcessBuilder].
   *
   * When serializing into a collection of CLI call params, string quoting rules
   * do not apply.
   *
   * The given object must either implement the [Command] interface, or be
   * annotated with [@CliCommand][CliCommand] to be serialized.  Providing a
   * value that does not meet at least one of those requirements will cause in
   * an exception to be thrown.
   *
   * @param cliCommand CLI call config to serialize.
   *
   * @param options Serialization options.
   *
   * @return A list of strings representing the serialized CLI call.
   *
   * @throws CliSerializationException If the given [cliCommand] value is not a
   * [Command] instance and is not annotated with [@CliCommand][CliCommand].
   */
  @JvmStatic
  @JvmOverloads
  @Throws(CliSerializationException::class)
  fun toCliCallList(cliCommand: Any, options: CliSerializationConfig = CliSerializationConfig()) =
    newCommandSerializer(options).serializeToList(cliCommand)

  /**
   * Attempts to serialize the given object into CLI call params that are then
   * fed into a new [ProcessBuilder] instance.
   *
   * The given object must either implement the [Command] interface, or be
   * annotated with [@CliCommand][CliCommand] to be serialized.  Providing a
   * value that does not meet at least one of those requirements will cause in
   * an exception to be thrown.
   *
   * @param cliCommand CLI call config to serialize.
   *
   * @param options Serialization options.
   *
   * @return A new [ProcessBuilder] instance configured by the CLI call
   * serialized from the given input object.
   *
   * @throws CliSerializationException If the given [cliCommand] value is not a
   * [Command] instance and is not annotated with [@CliCommand][CliCommand].
   */
  @JvmStatic
  @JvmOverloads
  @Throws(CliSerializationException::class)
  fun toProcessBuilder(cliCommand: Any, options: CliSerializationConfig = CliSerializationConfig()) =
    ProcessBuilder(toCliCallList(cliCommand, options))

  /**
   * Creates a new [CommandSerializer] instance with the given configuration.
   *
   * The returned instance may be used to serialize multiple commands with the
   * same options.
   *
   * @param options CLI call serialization options.
   */
  @JvmStatic
  @JvmOverloads
  fun newCommandSerializer(options: CliSerializationConfig = CliSerializationConfig()): CommandSerializer =
    CommandSerializerImpl(options)
}


