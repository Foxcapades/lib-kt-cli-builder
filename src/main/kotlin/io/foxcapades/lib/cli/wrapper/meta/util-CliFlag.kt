package io.foxcapades.lib.cli.wrapper.meta

import io.foxcapades.lib.cli.wrapper.ResolvedFlag
import io.foxcapades.lib.cli.wrapper.reflect.getOrCreate
import io.foxcapades.lib.cli.wrapper.serial.CliSerializationConfig
import io.foxcapades.lib.cli.wrapper.serial.DumbArgumentAppenderPool
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.serial.values.FlagDefaultTest
import io.foxcapades.lib.cli.wrapper.util.CHAR_NULL

const val CliFlagUnsetLongForm = ""

const val CliFlagUnsetShortForm = CHAR_NULL

const val CliComponentUnsetDefault = "!io.foxcapades.lib.cli.wrapper.meta.CliComponent.default"

inline val CliFlag.hasDefaultValue
  get() = default != CliComponentUnsetDefault

inline val CliFlag.hasShortForm
  get() = shortForm != CliFlagUnsetShortForm

inline val CliFlag.hasLongForm
  get() = longForm != CliFlagUnsetLongForm


@Suppress("UNCHECKED_CAST")
internal fun <V> CliFlagAnnotation.defaultCheck(
  value: V,
  reference: ResolvedFlag<*, V>,
  config: CliSerializationConfig,
) =
  (reference.hasDefault && default == DumbArgumentAppenderPool.use(config) {
    try {
      // TODO: what happens if the default checker expects a different type of
      //       value than the annotated property?
      (formatter.getOrCreate() as ArgumentFormatter<Any?>).formatValue(value, it)
    } catch (e: NullPointerException) {
      config.nullSerializer(it)
    }
  })
    || getTest<V>().valueIsDefault(value, reference, config)

@Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST")
internal inline fun <T> CliFlagAnnotation.getTest(): FlagDefaultTest<T> =
  defaultValueTest.getOrCreate() as FlagDefaultTest<T>

@Suppress("NOTHING_TO_INLINE")
internal inline fun CliFlag.wrap() = CliFlagAnnotation(this)
