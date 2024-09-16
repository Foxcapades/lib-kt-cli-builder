package io.foxcapades.lib.cli.builder.util.reflect

import kotlin.reflect.KCallable
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMembers
import kotlin.reflect.full.superclasses

internal class ClassInfo<T : Any>(val type: KClass<out T>) {

  val members = LinkedHashMap<String, MemberInfo<in T, KCallable<*>>>()

  init {
    @Suppress("UNCHECKED_CAST")
    val queue = ArrayDeque<KClass<*>>(4).apply { add(type as KClass<T>) }

    while (queue.isNotEmpty()) {
      val target = queue.removeFirst()

      for (member in target.declaredMembers) {
        @Suppress("UNCHECKED_CAST")
        when (val tmp = members[member.name]) {
          null -> members[member.name] = MemberInfo(member, target as KClass<T>)
          else -> (tmp as MemberInfo<T, KCallable<*>>).addLayer(member, target as KClass<T>)
        }
      }

      queue.addAll(target.superclasses)
    }
  }

  fun <R> mapFilterMembers(mapper: (MemberInfo<in T, KCallable<*>>) -> R?): Iterator<R> =
    iterator {
      for ((_, v) in members) {
        v.accessible = true
        val r = mapper(v) ?: continue
        yield(r)
      }
    }
}
