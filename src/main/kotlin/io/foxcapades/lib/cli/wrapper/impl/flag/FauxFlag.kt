package io.foxcapades.lib.cli.wrapper.impl.flag

import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.Flag
import io.foxcapades.lib.cli.wrapper.impl.arg.FauxArgument
import io.foxcapades.lib.cli.wrapper.meta.CliFlag
import io.foxcapades.lib.cli.wrapper.meta.hasLongForm
import io.foxcapades.lib.cli.wrapper.meta.hasShortForm
import io.foxcapades.lib.cli.wrapper.serial.CliStringBuilder
import io.foxcapades.lib.cli.wrapper.utils.BUG
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty1

internal class FauxFlag<T : Any>(
  private val instance: T,
  internal val annotation: CliFlag,
  private val property: KProperty1<T, *>,
  private val type: KClass<out T>,
) : Flag<Argument<Any?>, Any?> {
  override val hasLongForm
    get() = annotation.hasLongForm

  override val longForm
    get() = annotation.longForm

  override val hasShortForm
    get() = annotation.hasShortForm

  override val shortForm: Byte
    get() = annotation.shortForm.code.toByte()

  override val argument
    get() = FauxArgument(this)

  override val isRequired: Boolean
    get() = annotation.isRequired

  override fun get(): Any? = property.get(instance)

  override fun getValue(ref: Any?, property: KProperty<*>) = this.property.get(instance)

  override fun writeToString(builder: CliStringBuilder) {
    if (!hasLongForm || builder.config.preferredFlagForm.isShort && hasShortForm) {
      builder.appendShortFlag(shortForm, true)
    } else {
      builder.appendLongFlag(longForm, true)
    }

    argument.writeToString(builder)
  }

  override fun set(value: Any?) = BUG()
  override fun setValue(ref: Any?, property: KProperty<*>, value: Any?) = BUG()
  override fun unset() = BUG()
}