package io.foxcapades.lib.cli.builder.reflect

import io.foxcapades.lib.cli.builder.util.filter
import io.foxcapades.lib.cli.builder.util.onEach
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.superclasses
import kotlin.reflect.jvm.isAccessible

internal class ClassInfo<T : P, P : Any> private constructor(val type: KClass<P>) {

  companion object {
    @Suppress("UNCHECKED_CAST")
    fun <T : Any> new(type: KClass<out T>) = ClassInfo(type as KClass<T>)
  }

  @Suppress("UNCHECKED_CAST")
  val properties: Map<String, KProperty1<T, *>> =
    type.declaredMemberProperties.associate { it.name to it as KProperty1<T, *> }

  @Suppress("UNCHECKED_CAST")
  val parents: List<ClassInfo<T, Any>> = type.superclasses.map { ClassInfo(it as KClass<Any>) }

  fun annotatedProperties(type: KClass<out Annotation>): Iterator<KProperty<*>> =
    if (parents.isEmpty())
      declaredAnnotatedProperties(type)
    else
      resolveAnnotatedProperties(type)

  inline fun <reified T : Annotation> annotatedProperties() = annotatedProperties(T::class)

  fun <R> mapFilterProperties(
    mapper: (KProperty1<T, *>) -> R?,
    conflict: (prev: Triple<KClass<*>, KProperty1<T, *>, R>, next: Triple<KClass<*>, KProperty1<T, *>, R>) -> Triple<KClass<*>, KProperty1<T, *>, R>
  ): Iterator<R> =
    iterator {
      val seenGlobal = HashSet<String>(8)

      for ((k, v) in properties) {
        v.isAccessible = true
        val r = mapper(v) ?: continue
        seenGlobal.add(k)
        yield(r)
      }

      val seenOnLayer = HashMap<String, Triple<KClass<*>, KProperty1<T, *>, R>>(8)
      val layerQueue = ArrayDeque<List<ClassInfo<T, *>>>(8)
      layerQueue.add(parents)

      while (layerQueue.isNotEmpty()) {
        val layer = layerQueue.removeFirst()

        for (parent in layer) {
          for ((k, v) in parent.properties) {
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

  fun findProperties(
    filter: (KProperty<*>) -> Boolean,
    conflict: (prev: Pair<KClass<*>, KProperty1<T, *>>, next: Pair<KClass<*>, KProperty1<T, *>>) -> Pair<KClass<*>, KProperty1<T, *>>,
  ): Iterator<KProperty<*>> =
    iterator {
      val seenGlobal = HashSet<String>(8)

      for ((k, v) in properties) {
        if (filter(v)) {
          seenGlobal.add(k)
          v.isAccessible = true
          yield(v)
        }
      }

      val seenOnLayer = HashMap<String, Pair<KClass<*>, KProperty1<T, *>>>(8)
      val layerQueue = ArrayDeque<List<ClassInfo<T, in T>>>(8)
      layerQueue.add(parents)

      while (layerQueue.isNotEmpty()) {
        val layer = layerQueue.removeFirst()

        for (parent in layer) {
          for ((k, v) in parent.properties) {
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

  private fun declaredAnnotatedProperties(type: KClass<out Annotation>) =
    properties.values.iterator()
      .filter { it.hasAnnotation(type) }
      .onEach { it.isAccessible = true }

  private fun resolveAnnotatedProperties(type: KClass<out Annotation>) =
    findProperties(
      { it.hasAnnotation(type) },
      { p, n -> throw IllegalStateException(
        "conflicting annotations on supertypes %s and %s of class %s"
          .format(p.first.safeName, n.first.safeName, this.type.safeName)
      ) }
    )
}
