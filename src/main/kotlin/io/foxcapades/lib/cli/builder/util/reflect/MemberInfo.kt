package io.foxcapades.lib.cli.builder.util.reflect

import kotlin.reflect.KCallable
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotations
import kotlin.reflect.jvm.isAccessible

internal class MemberInfo<T : Any, K : KCallable<*>>(reference: K, type: KClass<T>) {
  private val layers = ArrayList<Pair<KClass<in T>, K>>(4)
    .also { it.add(type to reference) }

  val name = layers[0].second.name

  val type = layers[0].first

  val member = layers[0].second

  var accessible: Boolean
    get() = layers[0].second.isAccessible
    set(value) { layers[0].second.isAccessible = value }

  fun addLayer(reference: K, type: KClass<in T>) {
    layers.add(type to reference)
  }

  fun name(layer: Int) =
    layers[layer].second.name

  fun <T : Annotation> findAnnotations(type: KClass<out T>): Sequence<T> =
    sequence {
      val set = HashSet<Annotation>(4)
      for ((_, prop) in layers) {
        for (ann in prop.findAnnotations(type)) {
          if (ann !in set) {
            yield(ann)
            set.add(ann)
          }
        }
      }
    }

  fun annotations(): Sequence<Annotation> =
    sequence {
      val set = HashSet<Annotation>(4)
      for ((_, prop) in layers) {
        for (ann in prop.annotations) {
          if (ann !in set) {
            yield(ann)
            set.add(ann)
          }
        }
      }
    }

  @Suppress("UNCHECKED_CAST")
  operator fun component1(): KClass<T> = layers[0].first as KClass<T>

  operator fun component2(): KCallable<*> = layers[0].second
}
