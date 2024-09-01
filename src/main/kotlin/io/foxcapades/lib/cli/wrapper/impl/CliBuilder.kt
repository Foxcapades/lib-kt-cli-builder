package io.foxcapades.lib.cli.wrapper.impl

import io.foxcapades.lib.cli.wrapper.Flag
import io.foxcapades.lib.cli.wrapper.impl.flag.AnnotatedFlag
import io.foxcapades.lib.cli.wrapper.impl.flag.FauxFlag
import io.foxcapades.lib.cli.wrapper.meta.CliCommand
import io.foxcapades.lib.cli.wrapper.meta.CliFlag
import io.foxcapades.lib.cli.wrapper.meta.validateConfig
import io.foxcapades.lib.cli.wrapper.serial.CliCallComponent
import io.foxcapades.lib.cli.wrapper.serial.CliCallSerializable
import io.foxcapades.lib.cli.wrapper.serial.CliSerializationConfig
import io.foxcapades.lib.cli.wrapper.serial.CliStringBuilderImpl
import io.foxcapades.lib.cli.wrapper.utils.findInstance
import io.foxcapades.lib.cli.wrapper.utils.safeName
import io.foxcapades.lib.cli.wrapper.utils.takeAs
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

internal class CliBuilder<T : Any>(
  private val instance: T,
  private val options: CliSerializationConfig
) {
  private val type: KClass<out T> = instance::class

  private val commandLeader: Array<String> = when {
    instance is CliCallSerializable -> instance.cliCommand
    else -> {
      val ann: CliCommand = type.annotations.findInstance()
        // TODO: this should be its own exception type
        ?: throw IllegalArgumentException(
          "given type ${type.safeName} cannot be converted into a cli" +
            " string, must be annotated with @CliCommand and/or implement" +
            " CliCallSerializable")
      val tmp = arrayOfNulls<String>(ann.extras.size + 1)

      tmp[0] = ann.command
      ann.extras.copyInto(tmp, 1)

      @Suppress("UNCHECKED_CAST")
      tmp as Array<String>
    }
  }

  private val builder by lazy { CliStringBuilderImpl(options) }

  internal fun asSequence(): Sequence<String> = sequence {
    for (leader in commandLeader)
      yield(leader)

    yieldAll(siftProperties().map { stringify(it) })
  }

  internal fun asIterable() = asSequence().asIterable()

  internal fun asList() = asSequence().toList()

  internal fun asMutableList() = asSequence().toMutableList()

  internal fun asString(preSize: Int = 2048): String {
    val tmp = StringBuilder(preSize)
    val it  = asSequence().iterator()

    tmp.append(it.next())

    while (it.hasNext()) {
      tmp.append(' ').append(it.next())
    }

    return tmp.toString()
  }

  private fun stringify(component: CliCallComponent): String {
    component.writeToString(builder)
    return builder.toString()
      .also { builder.clear() }
  }

  private fun siftProperties() =
    type.memberProperties
      .asSequence()
      .map { @Suppress("UNCHECKED_CAST") (it as KProperty1<T, *>) }
      .onEach { it.isAccessible = true }
      .mapNotNull { siftProperty(it) }

  private fun siftProperty(prop: KProperty1<T, *>): CliCallComponent? {
    val del: CliCallComponent? = prop.getDelegate(instance).takeAs()
    val ann: CliFlag? = prop.annotations.findInstance()

    return if (del != null) {
      if (ann != null) {
        ann.validateConfig(type, prop, options)

        if (del is Flag<*, *>)
          AnnotatedFlag(ann, del)
        else
          TODO("annotation + argument")
      } else {
        del
      }
    } else if (ann != null) {
      ann.validateConfig(type, prop, options)

      FauxFlag(instance, ann, prop, type)
    } else {
      null
    }
  }
}
