package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.Flag
import io.foxcapades.lib.cli.wrapper.ResolvedFlag
import io.foxcapades.lib.cli.wrapper.meta.CliFlagAnnotation
import io.foxcapades.lib.cli.wrapper.meta.defaultCheck
import io.foxcapades.lib.cli.wrapper.reflect.AnnotatedPropertyReference
import io.foxcapades.lib.cli.wrapper.serial.CliAppender
import io.foxcapades.lib.cli.wrapper.serial.CliSerializationConfig
import io.foxcapades.lib.cli.wrapper.util.BUG
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty1

internal class AnnotatedFlag<T : Any>(
  override val type: KClass<out T>,
  override val property: KProperty1<T, *>,
  override val annotation: CliFlagAnnotation,
  private val delegate: Flag<*, *>,
) : ResolvedFlag<T, Any?>, AnnotatedPropertyReference<T, Any?, CliFlagAnnotation> {
  override val hasLongForm
    get() = annotation.hasLongForm || delegate.hasLongForm

  override val longForm
    get() = when {
      annotation.hasLongForm -> annotation.longForm
      delegate.hasLongForm   -> delegate.longForm
      else                   -> BUG()
    }

  override val hasShortForm
    get() = annotation.hasShortForm || delegate.hasShortForm

  override val shortForm
    get() =  when {
      annotation.hasShortForm -> annotation.shortForm
      delegate.hasShortForm   -> delegate.shortForm
      else                    -> BUG()
    }

  @Suppress("UNCHECKED_CAST")
  override val argument: Argument<Any?>
    get() = delegate.argument as Argument<Any?>

  override val isRequired: Boolean
    get() = annotation.required || delegate.isRequired

  override fun get() = delegate.get()

  override fun getValue(thisRef: Any?, property: KProperty<*>) = delegate.getValue(thisRef, property)

  override fun set(value: Any?) = BUG()

  override fun setValue(thisRef: Any?, property: KProperty<*>, value: Any?) = BUG()

  override fun unset() = BUG()

  override fun isDefault(config: CliSerializationConfig): Boolean {
    if (delegate.hasDefault)
      return delegate.isDefault(config)

    return annotation.defaultCheck(get(), this, config)
  }

  override fun writeToString(builder: CliAppender) {
    if (delegate is BooleanFlag && delegate.isToggleFlag) {
      // TODO: if a default value is set on both the annotation and the delegate
      //       throw an exception.
      if (isDefault(builder.config))
        return

      if (!hasLongForm || builder.config.preferredFlagForm.isShort && hasShortForm) {
        builder.putShortFlag(shortForm, false)
      } else {
        builder.putLongFlag(longForm, false)
      }
    } else {
      if (!hasLongForm || builder.config.preferredFlagForm.isShort && hasShortForm) {
        builder.putShortFlag(shortForm, true)
      } else {
        builder.putLongFlag(longForm, true)
      }

      builder.putArgument(argument)
    }
  }
}
