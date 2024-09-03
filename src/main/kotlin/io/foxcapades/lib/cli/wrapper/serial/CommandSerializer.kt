package io.foxcapades.lib.cli.wrapper.serial

import kotlin.math.max

interface CommandSerializer<T : Any> {
  val config: CliSerializationConfig

  fun serializeToIterator(): Iterator<String>

  fun serializeToSequence(): Sequence<String> =
    Sequence(::serializeToIterator)

  fun serializeToIterable(): Iterable<String> =
    Iterable(::serializeToIterator)

  fun serializeToList(): List<String> =
    serializeToList(32)

  fun serializeToList(preSize: Int): List<String> =
    serializeToArray(preSize).asList()

  fun serializeToMutableList(): MutableList<String> =
    serializeToMutableList(32)

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