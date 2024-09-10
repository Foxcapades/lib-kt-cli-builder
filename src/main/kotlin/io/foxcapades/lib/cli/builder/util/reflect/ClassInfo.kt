package io.foxcapades.lib.cli.builder.util.reflect

import kotlin.reflect.KCallable
import kotlin.reflect.KClass
import kotlin.reflect.KFunction1
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMembers
import kotlin.reflect.full.superclasses
import kotlin.reflect.jvm.isAccessible

internal class ClassInfo(val type: KClass<out Any>) {

  val members = HashMap<String, KCallable<*>>()

  val parents: List<ClassInfo> = type.superclasses.map { ClassInfo(it) }

  init {
    for (member in type.declaredMembers) {
      when (member) {
        is KProperty1<*, *> -> members[member.name] = member.unsafeCast<Any, Any?>()
        is KFunction1<*, *> -> if (member.parameters[0].type.classifier == type) {
          members[member.name] = member.unsafeCast<Any, Any?>()
        }
      }
    }
  }

  fun <R> mapFilterMembers(
    mapper:   (KCallable<*>) -> R?,
    conflict: (prev: Triple<KClass<*>, KCallable<*>, R>, next: Triple<KClass<*>, KCallable<*>, R>) -> Triple<KClass<*>, KCallable<*>, R>
  ): Iterator<R> =
    iterator {
      val seenGlobal = HashSet<String>(8)

      for ((k, v) in members) {
        v.isAccessible = true
        val r = mapper(v) ?: continue
        seenGlobal.add(k)
        yield(r)
      }

      val seenOnLayer = HashMap<String, Triple<KClass<*>, KCallable<*>, R>>(8)
      val layerQueue = ArrayDeque<List<ClassInfo>>(8)
      layerQueue.add(parents)

      while (layerQueue.isNotEmpty()) {
        val layer = layerQueue.removeFirst()

        for (parent in layer) {
          for ((k, v) in parent.members) {
            if (k in seenGlobal)
              continue

            v.isAccessible = true

            val r = mapper(v) ?: continue

            val ref = Triple(type, v, r)

            seenOnLayer[k] = when (val tmp = seenOnLayer[k]) {
              null -> ref
              else -> conflict(tmp, ref)
            }

            yield(r)
          }

          layerQueue.add(parent.parents)
        }

        seenGlobal.addAll(seenOnLayer.keys)
        seenOnLayer.clear()
      }
    }


  // region Reflection

  private fun <R, P : KCallable<*>> mapFilterMembers(
    mapper:   (P) -> R?,
    conflict: (prev: Triple<KClass<*>, P, R>, next: Triple<KClass<*>, P, R>) -> Triple<KClass<*>, P, R>,
    accessor: KProperty1<ClassInfo, Map<String, P>>,
  ): Iterator<R> =
    iterator {
      val seenGlobal = HashSet<String>(8)

      for ((k, v) in accessor.get(this@ClassInfo)) {
        v.isAccessible = true
        val r = mapper(v) ?: continue
        seenGlobal.add(k)
        yield(r)
      }

      val seenOnLayer = HashMap<String, Triple<KClass<*>, P, R>>(8)
      val layerQueue = ArrayDeque<List<ClassInfo>>(8)
      layerQueue.add(parents)

      while (layerQueue.isNotEmpty()) {
        val layer = layerQueue.removeFirst()

        for (parent in layer) {
          for ((k, v) in accessor.get(parent)) {
            if (k in seenGlobal)
              continue

            v.isAccessible = true

            val r = mapper(v) ?: continue

            val ref = Triple(type, v, r)

            seenOnLayer[k] = when (val tmp = seenOnLayer[k]) {
              null -> ref
              else -> conflict(tmp, ref)
            }

            yield(r)
          }

          layerQueue.add(parent.parents)
        }

        seenGlobal.addAll(seenOnLayer.keys)
        seenOnLayer.clear()
      }
    }

  private fun <P : KCallable<*>> findMembers(
    filter:   (P) -> Boolean,
    conflict: (prev: Pair<KClass<*>, P>, next: Pair<KClass<*>, P>) -> Pair<KClass<*>, P>,
    accessor: KProperty1<ClassInfo, Map<String, P>>,
  ): Iterator<P> =
    iterator {
      val seenGlobal = HashSet<String>(8)

      for ((k, v) in accessor.get(this@ClassInfo)) {
        if (filter(v)) {
          seenGlobal.add(k)
          v.isAccessible = true
          yield(v)
        }
      }

      val seenOnLayer = HashMap<String, Pair<KClass<*>, P>>(8)
      val layerQueue = ArrayDeque<List<ClassInfo>>(8)
      layerQueue.add(parents)

      while (layerQueue.isNotEmpty()) {
        val layer = layerQueue.removeFirst()

        for (parent in layer) {
          for ((k, v) in accessor.get(parent)) {
            if (k in seenGlobal)
              continue

            if (!filter(v))
              continue

            v.isAccessible = true

            val ref = parent.type to v

            seenOnLayer[k] = when (val tmp = seenOnLayer[k]) {
              null -> ref
              else -> conflict(tmp, ref)
            }

            yield(v)
          }

          layerQueue.add(parent.parents)
        }

        seenGlobal.addAll(seenOnLayer.keys)
        seenOnLayer.clear()
      }
    }

  // endregion Reflection
}
