package io.foxcapades.lib.cli.wrapper.serial

import io.foxcapades.lib.cli.wrapper.*
import io.foxcapades.lib.cli.wrapper.flag.AnnotatedFlag
import io.foxcapades.lib.cli.wrapper.flag.FauxFlag
import io.foxcapades.lib.cli.wrapper.flag.PinnedFlag
import io.foxcapades.lib.cli.wrapper.meta.CliCommand
import io.foxcapades.lib.cli.wrapper.meta.CliFlag
import io.foxcapades.lib.cli.wrapper.meta.wrap
import io.foxcapades.lib.cli.wrapper.reflect.ClassInfo
import io.foxcapades.lib.cli.wrapper.reflect.asDelegateType
import io.foxcapades.lib.cli.wrapper.util.*
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

  private val emptyValue by lazy { StringBuilder(8).also {
    config.targetShell.startString(it::append)
    config.targetShell.endString(it::append)
  }.toString() }

  override fun serializeToIterator() = iterator {
    for (leader in commandLeader)
      yield(leader)

    val serializer = LazyCliAppenderImpl(filterUsableProperties(), config)
    val buffer = StringBuilder(1024)
    var last: CliSegment? = null

    while (serializer.hasNext()) {
      val segment = serializer.next()

      if (segment.isFlag) {
        appendFlag(last, segment, buffer)
      } else {
        appendValue(last, segment.value, buffer)
      }

      last = segment
    }

    if (last?.hasValue == true) {
      maybeAppendEmpty(last, buffer)
    }
  }

  private suspend fun SequenceScope<String>.appendFlag(last: CliSegment?, flag: CliSegment, buffer: StringBuilder) {
    maybeAppendEmpty(last, buffer)

    if (flag.isLongFlag)
      buffer.append(config.longFlagPrefix).append(flag.value)
    else
      buffer.append(config.shortFlagPrefix).append(flag.value)

    if (!flag.hasValue)
      yield(buffer.dump())
  }

  private suspend fun SequenceScope<String>.appendValue(last: CliSegment?, value: String, buffer: StringBuilder) {
    if (last?.hasValue == true)
      appendValueFor(last, value, buffer)
    else
      yield(value)
  }

  private suspend fun SequenceScope<String>.maybeAppendEmpty(last: CliSegment?, buffer: StringBuilder) {
    if (last?.hasValue == true)
      appendValueFor(last, emptyValue, buffer)
  }

  private suspend fun SequenceScope<String>.appendValueFor(flag: CliSegment, value: String, buffer: StringBuilder) {
    val divider = if (flag.isLongFlag)
      config.longFlagValueSeparator
    else
      config.shortFlagValueSeparator

    if (divider.isSplitableValueDivider) {
      yield(buffer.dump())
      yield(value)
    } else {
      buffer.append(divider).append(value)
      yield(buffer.dump())
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
      if (isDefault(config)) {
        // FIXME: get info about the flag to show an error!
        throw IllegalArgumentException("bad flag, naughty naughty")
      }

      true
    } else if (isDefault(config)) {
      when (config.includeDefaultedFlags) {
        IncludeDefault.Always          -> true
        IncludeDefault.Never           -> false
        IncludeDefault.IfSetExplicitly -> argument.isSet
      }
    } else {
      true
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
          AnnotatedFlag(type, prop, ann.wrap(), del)
        else
          TODO("annotation + argument")
      } else if (del is Flag<*, *>) {
        PinnedFlag(type, prop, del)
      } else {
        TODO("pinned annotation")
      }
    } else if (ann != null) {
      FauxFlag(instance, ann.wrap(), prop, type)
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
