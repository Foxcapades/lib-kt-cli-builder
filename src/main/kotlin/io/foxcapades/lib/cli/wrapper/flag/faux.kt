package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.ResolvedFlag
import io.foxcapades.lib.cli.wrapper.arg.FauxArgument
import io.foxcapades.lib.cli.wrapper.meta.CliFlagAnnotation
import io.foxcapades.lib.cli.wrapper.reflect.AnnotatedPropertyReference
import io.foxcapades.lib.cli.wrapper.reflect.getOrCreate
import io.foxcapades.lib.cli.wrapper.serial.CliAppender
import io.foxcapades.lib.cli.wrapper.serial.CliSerializationConfig
import io.foxcapades.lib.cli.wrapper.serial.values.FlagDefaultTest
import io.foxcapades.lib.cli.wrapper.util.BUG
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

internal class FauxFlag<T : Any>(
  internal val instance: T,
  override val annotation: CliFlagAnnotation,
  override val property: KProperty1<T, *>,
  override val type: KClass<out T>,
) : ResolvedFlag<T, Any?>, AnnotatedPropertyReference<T, Any?, CliFlagAnnotation> {
  private var defaultFlag: Byte = 2

  override val hasLongForm
    get() = annotation.hasLongForm

  override val longForm
    get() = annotation.longForm

  override val hasShortForm
    get() = annotation.hasShortForm

  override val shortForm
    get() = annotation.shortForm

  override val argument by lazy { FauxArgument(this) }

  override val isRequired
    get() = annotation.required

  override val hasDefault: Boolean
    get() = true

  override val isSet
    get() = defaultFlag == 0.toByte() || get() != null

  override fun get() = property.get(instance)

  @Suppress("UNCHECKED_CAST")
  override fun isDefault(config: CliSerializationConfig) =
    when (defaultFlag) {
      0.toByte() -> false
      1.toByte() -> true
      else       -> (annotation.defaultValueTest.getOrCreate() as FlagDefaultTest<Any?>)
        .valueIsDefault(property.get(instance), this, config)
        .also { defaultFlag = if (it) 1 else 0 }
    }

  override fun writeToString(builder: CliAppender) {
    if (!hasLongForm || builder.config.preferredFlagForm.isShort && hasShortForm) {
      builder.putShortFlag(shortForm, true)
    } else {
      builder.putLongFlag(longForm, true)
    }

    builder.putArgument(argument)
  }

  override fun set(value: Any?) = BUG()
  override fun unset() = BUG()
}
