package io.foxcapades.lib.cli.builder.serial

import io.foxcapades.lib.cli.builder.component.CliCallComponent
import io.foxcapades.lib.cli.builder.InvalidFlagFormException
import io.foxcapades.lib.cli.builder.component.ResolvedComponent
import io.foxcapades.lib.cli.builder.flag.impl.AnnotatedFlag
import io.foxcapades.lib.cli.builder.flag.impl.FauxFlag
import io.foxcapades.lib.cli.builder.flag.impl.PinnedFlag
import io.foxcapades.lib.cli.builder.command.CliCommand
import io.foxcapades.lib.cli.builder.flag.*
import io.foxcapades.lib.cli.builder.reflect.ClassInfo
import io.foxcapades.lib.cli.builder.reflect.asDelegateType
import io.foxcapades.lib.cli.builder.util.filter
import io.foxcapades.lib.cli.builder.util.findInstance
import io.foxcapades.lib.cli.builder.util.onEach
import io.foxcapades.lib.cli.builder.reflect.safeName
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.findAnnotation

internal class CommandSerializerImpl<T : Any>(
  private val instance: T,
  override val config: CliSerializationConfig
) : CommandSerializer<T> {
  private val type: KClass<out T> = instance::class

  private val typeInfo: ClassInfo<T, T> = ClassInfo.new(type)

  private inline val commandLeader
    get() = when {
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

  // region Iterator

  override fun serializeToIterator() = iterator {
    for (leader in commandLeader)
      yield(leader)

    val serializer = LazyCliAppenderImpl(filterUsableProperties(), config)
    while (serializer.hasNext()) {
      val segment = serializer.next()

      yield(segment)
    }
  }

  // endregion Iterator

  // region Stringify

  override fun serializeToString(preSize: Int) =
    StringCliAppenderImpl(commandLeader, filterUsableProperties(), config, preSize).toString()

  // endregion Stringify

  // region Component Resolution

  private fun filterValidProperties() =
    filterUsableProperties().onEach {
      if (it is ResolvedFlag)
        validateFlagNames(it)
    }

  private fun filterUsableProperties() =
    filterRelevantProperties().filter { it.isUsable }

  private inline val ResolvedComponent<T, Any?>.isUsable get() =
    if (this is ResolvedFlag) this.isUsable() else false

  private fun ResolvedFlag<T, Any?>.isUsable() =
    if (isRequired) {
      // TODO: interplay: flag vs argument requirements
      if (isSet) {
        true
      } else if (hasDefault) {
        true
      } else {
        // TODO: make this error message actually useful
        throw IllegalArgumentException("bad flag, naughty naughty")
      }
    } else {
      shouldSerialize(config, this)
    }

  private fun filterRelevantProperties() =
    typeInfo.mapFilterProperties(::siftProperty) { p, n ->
      throw IllegalStateException(
        "conflicting annotations on supertypes %s and %s of class %s"
          .format(p.first.safeName, n.first.safeName, this.type.safeName)
      )
    }

  private fun siftProperty(prop: KProperty1<T, *>): ResolvedComponent<T, Any?>? {
    val del = prop.asDelegateType<T, CliCallComponent>(instance)
    val ann = prop.findAnnotation<CliFlag>()

    return if (del != null) {
      if (ann != null) {
        if (del is Flag<*, *>)
          AnnotatedFlag(type, instance, prop, CliFlagAnnotation(ann), del.unsafeAnyType())
        else
          TODO("annotation + argument")
      } else if (del is Flag<*, *>) {
        PinnedFlag(type, instance, prop, del)
      } else {
        TODO("pinned annotation")
      }
    } else if (ann != null) {
      FauxFlag(instance, CliFlagAnnotation(ann), prop, type)
    } else {
      null
    }
  }

  // endregion Component Resolution

  // region Validation

  private fun validateFlagNames(flag: ResolvedFlag<T, Any?>) {
    val sv = !flag.hasShortForm || config.targetShell.isFlagSafe(flag.shortForm)
    var lv = true

    if (flag.hasLongForm) {
      for (c in flag.longForm) {
        if (!config.targetShell.isFlagSafe(c)) {
          lv = false
          break
        }
      }
    }

    if (!sv) {
      throw if (!lv)
        InvalidFlagFormException.invalidBothForms(flag)
      else
        InvalidFlagFormException.invalidShortForm(flag)
    } else if (!lv) {
      throw InvalidFlagFormException.invalidLongForm(flag)
    }
  }

  // endregion Validation
}
