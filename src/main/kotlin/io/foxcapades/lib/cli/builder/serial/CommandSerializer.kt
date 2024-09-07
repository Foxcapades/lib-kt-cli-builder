package io.foxcapades.lib.cli.builder.serial

import kotlin.math.max

/**
 * Defines a type used to serialize command line calls.
 *
 * @since 1.0.0
 */
interface CommandSerializer<T : Any> {
  val config: CliSerializationConfig

  /**
   * Serializes the CLI call into an iterator of CLI call parts.
   *
   * The returned iterator will always contain at least one value, the name of
   * or path to the executable command.
   *
   * @return Iterator of CLI call parts.
   */
  fun serializeToIterator(): Iterator<String>

  /**
   * Serializes the CLI call into a sequence of CLI call parts.
   *
   * The returned sequence will always contain at least one value, the name of
   * or path to the executable command.
   *
   * @return Sequence of CLI call parts.
   */
  fun serializeToSequence(): Sequence<String> =
    Sequence(::serializeToIterator)

  /**
   * Serializes the CLI call into an iterable of CLI call parts.
   *
   * The returned iterable will always contain at least one value, the name of
   * or path to the executable command.
   *
   * @return Iterable of CLI call parts.
   */
  fun serializeToIterable(): Iterable<String> =
    Iterable(::serializeToIterator)

  /**
   * Serializes the CLI call into a `List` of CLI call parts.
   *
   * The returned `List` will always contain at least one value, the name of
   * or path to the executable command.
   *
   * @return List of CLI call parts.
   *
   * @see serializeToMutableList
   */
  fun serializeToList(): List<String> =
    serializeToList(16)

  /**
   * Serializes the CLI call into a pre-sized `List` of CLI call parts.
   *
   * The returned `List` will always contain at least one value, the name of
   * or path to the executable command.
   *
   * @param preSize Starting size to use when constructing the returned list,
   * may be used to reduce reallocations in situations where the size of the
   * generated command is likely to contain many elements.
   *
   * @return List of CLI call parts.
   *
   * @see serializeToMutableList
   */
  fun serializeToList(preSize: Int): List<String> =
    serializeToArray(preSize).asList()

  /**
   * Serializes the CLI call into a `MutableList` of CLI call parts.
   *
   * The returned `List` will always contain at least one value, the name of
   * or path to the executable command.
   *
   * @return Mutable list of CLI call parts.
   *
   * @see serializeToList
   */
  fun serializeToMutableList(): MutableList<String> =
    serializeToMutableList(32)

  /**
   * Serializes the CLI call into a pre-sized `MutableList` of CLI call parts.
   *
   * The returned `List` will always contain at least one value, the name of
   * or path to the executable command.
   *
   * @param preSize Starting size to use when constructing the returned list,
   * may be used to reduce reallocations in situations where the size of the
   * generated command is likely to contain many elements.
   *
   * @return Mutable list of CLI call parts.
   *
   * @see serializeToList
   */
  fun serializeToMutableList(preSize: Int): MutableList<String> =
    ArrayList<String>(preSize).apply { serializeToIterator().forEach { add(it) } }

  fun serializeToArray(): Array<String> =
    serializeToArray(32)

  fun serializeToArray(preSize: Int): Array<String> {
    var pos = 0
    var tmp = arrayOfNulls<String>(max(8, preSize))
    val seq = serializeToIterator()

    while (seq.hasNext()) {
      if (pos == tmp.size)
        tmp = tmp.copyOf(pos ushr 1)

      tmp[pos++] = seq.next()
    }

    @Suppress("UNCHECKED_CAST")
    return (if (pos == tmp.size) tmp else tmp.copyOf(pos)) as Array<String>
  }

  fun serializeToString(): String =
    serializeToString(2048)

  fun serializeToString(preSize: Int): String
}
