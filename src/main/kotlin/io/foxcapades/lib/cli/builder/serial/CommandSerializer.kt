package io.foxcapades.lib.cli.builder.serial

import io.foxcapades.lib.cli.builder.command.CliCommand
import io.foxcapades.lib.cli.builder.command.Command
import kotlin.math.max

/**
 * Defines a type used to serialize command line calls.
 *
 * @since 1.0.0
 */
interface CommandSerializer {
  /**
   * Configuration for the [CommandSerializer] instance.
   */
  val config: CliSerializationConfig

  /**
   * Serializes a CLI call into an iterator of CLI call parts.
   *
   * The returned iterator will always contain at least one value, the name of
   * or path to the executable command.
   *
   * **IMPORTANT**: When serializing to `Iterator`, string quoting and escaping
   * rules are not applied!
   *
   * The given object must either implement the [Command] interface, or be
   * annotated with [@CliCommand][CliCommand] to be serialized.  Providing a
   * value that does not meet at least one of those requirements will cause in
   * an exception to be thrown.
   *
   * @param command Object to serialize.
   *
   * @return Iterator of CLI call parts.
   *
   * @throws IllegalArgumentException If the given [cliCommand] value is not a
   * [Command] instance and is not annotated with [@CliCommand][CliCommand].
   */
  @Throws(IllegalArgumentException::class)
  fun serializeToIterator(command: Any): Iterator<String>

  /**
   * Serializes a CLI call into a sequence of CLI call parts.
   *
   * The returned sequence will always contain at least one value, the name of
   * or path to the executable command.
   *
   * **IMPORTANT**: When serializing to `Sequence`, string quoting and escaping
   * rules are not applied!
   *
   * The given object must either implement the [Command] interface, or be
   * annotated with [@CliCommand][CliCommand] to be serialized.  Providing a
   * value that does not meet at least one of those requirements will cause in
   * an exception to be thrown.
   *
   * @param command Object to serialize.
   *
   * @return Sequence of CLI call parts.
   *
   * @throws IllegalArgumentException If the given [cliCommand] value is not a
   * [Command] instance and is not annotated with [@CliCommand][CliCommand].
   */
  @Throws(IllegalArgumentException::class)
  fun serializeToSequence(command: Any): Sequence<String> =
    Sequence { serializeToIterator(command) }

  /**
   * Serializes a CLI call into an iterable of CLI call parts.
   *
   * The returned iterable will always contain at least one value, the name of
   * or path to the executable command.
   *
   * **IMPORTANT**: When serializing to `Iterable`, string quoting and escaping
   * rules are not applied!
   *
   * The given object must either implement the [Command] interface, or be
   * annotated with [@CliCommand][CliCommand] to be serialized.  Providing a
   * value that does not meet at least one of those requirements will cause in
   * an exception to be thrown.
   *
   * @param command Object to serialize.
   *
   * @return Iterable of CLI call parts.
   *
   * @throws IllegalArgumentException If the given [cliCommand] value is not a
   * [Command] instance and is not annotated with [@CliCommand][CliCommand].
   */
  @Throws(IllegalArgumentException::class)
  fun serializeToIterable(command: Any): Iterable<String> =
    Iterable { serializeToIterator(command) }

  /**
   * Serializes a CLI call into a `List` of CLI call parts.
   *
   * The returned `List` will always contain at least one value, the name of
   * or path to the executable command.
   *
   * **IMPORTANT**: When serializing to `List`, string quoting and escaping
   * rules are not applied!
   *
   * The given object must either implement the [Command] interface, or be
   * annotated with [@CliCommand][CliCommand] to be serialized.  Providing a
   * value that does not meet at least one of those requirements will cause in
   * an exception to be thrown.
   *
   * @param command Object to serialize.
   *
   * @return List of CLI call parts.
   *
   * @see serializeToMutableList
   *
   * @throws IllegalArgumentException If the given [cliCommand] value is not a
   * [Command] instance and is not annotated with [@CliCommand][CliCommand].
   */
  @Throws(IllegalArgumentException::class)
  fun serializeToList(command: Any): List<String> =
    serializeToList(command, 16)

  /**
   * Serializes a CLI call into a pre-sized `List` of CLI call parts.
   *
   * The returned `List` will always contain at least one value, the name of
   * or path to the executable command.
   *
   * **IMPORTANT**: When serializing to `List`, string quoting and escaping
   * rules are not applied!
   *
   * The given object must either implement the [Command] interface, or be
   * annotated with [@CliCommand][CliCommand] to be serialized.  Providing a
   * value that does not meet at least one of those requirements will cause in
   * an exception to be thrown.
   *
   * @param command Object to serialize.
   *
   * @param preSize Starting size to use when constructing the returned list.
   * This value may be used to reduce reallocations in situations where the size
   * of the generated command is likely to contain many elements.
   *
   * The output list size will be dependent on the number of elements actually
   * serialized as part of the call.
   *
   * @return List of CLI call parts.
   *
   * @see serializeToMutableList
   *
   * @throws IllegalArgumentException If the given [cliCommand] value is not a
   * [Command] instance and is not annotated with [@CliCommand][CliCommand].
   */
  @Throws(IllegalArgumentException::class)
  fun serializeToList(command: Any, preSize: Int): List<String> =
    serializeToArray(command, preSize).asList()

  /**
   * Serializes a CLI call into a `MutableList` of CLI call parts.
   *
   * The returned `List` will always contain at least one value, the name of
   * or path to the executable command.
   *
   * **IMPORTANT**: When serializing to `List`, string quoting and escaping
   * rules are not applied!
   *
   * The given object must either implement the [Command] interface, or be
   * annotated with [@CliCommand][CliCommand] to be serialized.  Providing a
   * value that does not meet at least one of those requirements will cause in
   * an exception to be thrown.
   *
   * @param command Object to serialize.
   *
   * @return Mutable list of CLI call parts.
   *
   * @see serializeToList
   *
   * @throws IllegalArgumentException If the given [cliCommand] value is not a
   * [Command] instance and is not annotated with [@CliCommand][CliCommand].
   */
  @Throws(IllegalArgumentException::class)
  fun serializeToMutableList(command: Any): MutableList<String> =
    serializeToMutableList(command, 16)

  /**
   * Serializes a CLI call into a pre-sized `MutableList` of CLI call parts.
   *
   * The returned `List` will always contain at least one value, the name of
   * or path to the executable command.
   *
   * **IMPORTANT**: When serializing to `List`, string quoting and escaping
   * rules are not applied!
   *
   * The given object must either implement the [Command] interface, or be
   * annotated with [@CliCommand][CliCommand] to be serialized.  Providing a
   * value that does not meet at least one of those requirements will cause in
   * an exception to be thrown.
   *
   * @param command Object to serialize.
   *
   * @param preSize Starting size to use when constructing the returned list.
   * This value may be used to reduce reallocations in situations where the size
   * of the generated command is likely to contain many elements.
   *
   * The output list size will be dependent on the number of elements actually
   * serialized as part of the call.
   *
   * @return Mutable list of CLI call parts.
   *
   * @see serializeToList
   *
   * @throws IllegalArgumentException If the given [cliCommand] value is not a
   * [Command] instance and is not annotated with [@CliCommand][CliCommand].
   */
  @Throws(IllegalArgumentException::class)
  fun serializeToMutableList(command: Any, preSize: Int): MutableList<String> =
    ArrayList<String>(max(8, preSize)).apply { serializeToIterator(command).forEach { add(it) } }

  /**
   * Serializes a CLI call into an array of CLI call parts.
   *
   * The returned array will always contain at least one value, the name of
   * or path to the executable command.
   *
   * The given object must either implement the [Command] interface, or be
   * annotated with [@CliCommand][CliCommand] to be serialized.  Providing a
   * value that does not meet at least one of those requirements will cause in
   * an exception to be thrown.
   *
   * **IMPORTANT**: When serializing to `Array`, string quoting and escaping
   * rules are not applied!
   *
   * @param command Object to serialize.
   *
   * @return Array of CLI call parts.
   *
   * @throws IllegalArgumentException If the given [cliCommand] value is not a
   * [Command] instance and is not annotated with [@CliCommand][CliCommand].
   */
  @Throws(IllegalArgumentException::class)
  fun serializeToArray(command: Any): Array<String> =
    serializeToArray(command, 16)

  /**
   * Serializes a CLI call into an array of CLI call parts.
   *
   * The returned array will always contain at least one value, the name of
   * or path to the executable command.
   *
   * **IMPORTANT**: When serializing to `Array`, string quoting and escaping
   * rules are not applied!
   *
   * The given object must either implement the [Command] interface, or be
   * annotated with [@CliCommand][CliCommand] to be serialized.  Providing a
   * value that does not meet at least one of those requirements will cause in
   * an exception to be thrown.
   *
   * @param command Object to serialize.
   *
   * @param preSize Starting size to use when constructing the returned array.
   * This value may be used to reduce reallocations in situations where the size
   * of the generated command is likely to contain many elements.
   *
   * The output array size will be dependent on the number of elements actually
   * serialized as part of the call.
   *
   * @return Array of CLI call parts.
   *
   * @throws IllegalArgumentException If the given [cliCommand] value is not a
   * [Command] instance and is not annotated with [@CliCommand][CliCommand].
   */
  @Throws(IllegalArgumentException::class)
  fun serializeToArray(command: Any, preSize: Int): Array<String> {
    var pos = 0
    var tmp = arrayOfNulls<String>(max(8, preSize))
    val seq = serializeToIterator(command)

    while (seq.hasNext()) {
      if (pos == tmp.size)
        tmp = tmp.copyOf(pos ushr 1)

      tmp[pos++] = seq.next()
    }

    @Suppress("UNCHECKED_CAST")
    return (if (pos == tmp.size) tmp else tmp.copyOf(pos)) as Array<String>
  }

  /**
   * Serializes a CLI call into a full call string.
   *
   * The given object must either implement the [Command] interface, or be
   * annotated with [@CliCommand][CliCommand] to be serialized.  Providing a
   * value that does not meet at least one of those requirements will cause in
   * an exception to be thrown.
   *
   * @param command Object to serialize.
   *
   * @return Serialized CLI call string.
   *
   * @throws IllegalArgumentException If the given [cliCommand] value is not a
   * [Command] instance and is not annotated with [@CliCommand][CliCommand].
   */
  @Throws(IllegalArgumentException::class)
  fun serializeToString(command: Any): String =
    serializeToString(command, 2048)

  /**
   * Serializes a CLI call into a full call string.
   *
   * The given object must either implement the [Command] interface, or be
   * annotated with [@CliCommand][CliCommand] to be serialized.  Providing a
   * value that does not meet at least one of those requirements will cause in
   * an exception to be thrown.
   *
   * @param command Object to serialize.
   *
   * @param preSize Starting size to use when initializing a buffer with which
   * the CLI call string will be built.  This value may be used to reduce
   * allocations in situations where the size of the generated command string is
   * likely to be larger than the default [preSize] value of `2048` characters.
   *
   * @return Serialized CLI call string.
   *
   * @throws IllegalArgumentException If the given [cliCommand] value is not a
   * [Command] instance and is not annotated with [@CliCommand][CliCommand].
   */
  @Throws(IllegalArgumentException::class)
  fun serializeToString(command: Any, preSize: Int): String
}
