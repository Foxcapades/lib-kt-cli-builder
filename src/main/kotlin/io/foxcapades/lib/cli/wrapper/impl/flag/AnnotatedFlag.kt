package io.foxcapades.lib.cli.wrapper.impl.flag

import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.Flag
import io.foxcapades.lib.cli.wrapper.flag.BooleanFlag
import io.foxcapades.lib.cli.wrapper.meta.CliFlag
import io.foxcapades.lib.cli.wrapper.meta.hasLongForm
import io.foxcapades.lib.cli.wrapper.meta.hasShortForm
import io.foxcapades.lib.cli.wrapper.serial.CliStringBuilder
import io.foxcapades.lib.cli.wrapper.utils.BUG
import kotlin.reflect.KProperty

internal class AnnotatedFlag(
  private val annotation: CliFlag,
  private val delegate: Flag<*, *>,
) : Flag<Argument<Any?>, Any?> {
  override val hasLongForm: Boolean
    get() = annotation.hasLongForm || delegate.hasLongForm

  override val longForm: String
    get() = when {
      annotation.hasLongForm -> annotation.longForm
      delegate.hasLongForm   -> delegate.longForm
      else                   -> BUG()
    }

  override val hasShortForm: Boolean
    get() = annotation.hasShortForm || delegate.hasShortForm

  override val shortForm: Byte
    get() =  when {
      annotation.hasShortForm -> annotation.shortForm.code.toByte()
      delegate.hasShortForm   -> delegate.shortForm
      else                    -> BUG()
    }

  @Suppress("UNCHECKED_CAST")
  override val argument: Argument<Any?>
    get() = delegate.argument as Argument<Any?>

  override val isRequired: Boolean
    get() = annotation.isRequired || delegate.isRequired

  override fun get() = delegate.get()

  override fun getValue(ref: Any?, property: KProperty<*>) = delegate.getValue(ref, property)

  override fun set(value: Any?) = BUG()

  override fun setValue(ref: Any?, property: KProperty<*>, value: Any?) = BUG()

  override fun unset() = BUG()

  override fun writeToString(builder: CliStringBuilder) {
    if (delegate is BooleanFlag && delegate.isToggleFlag) {
      if (argument.isDefault)
        return

      if (!hasLongForm || builder.config.preferredFlagForm.isShort && hasShortForm) {
        builder.appendShortFlag(shortForm, false)
      } else {
        builder.appendLongFlag(longForm, false)
      }
    } else {
      if (!hasLongForm || builder.config.preferredFlagForm.isShort && hasShortForm) {
        builder.appendShortFlag(shortForm, true)
      } else {
        builder.appendLongFlag(longForm, true)
      }

      argument.writeToString(builder)
    }
  }
}