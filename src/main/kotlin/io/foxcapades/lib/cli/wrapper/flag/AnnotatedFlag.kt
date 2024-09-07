package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.Flag
import io.foxcapades.lib.cli.wrapper.ResolvedFlag
import io.foxcapades.lib.cli.wrapper.meta.CliFlagAnnotation
import io.foxcapades.lib.cli.wrapper.reflect.AnnotatedValueAccessorReference
import io.foxcapades.lib.cli.wrapper.reflect.ValueAccessorReference
import io.foxcapades.lib.cli.wrapper.reflect.getOrCreate
import io.foxcapades.lib.cli.wrapper.serial.CliFlagWriter
import io.foxcapades.lib.cli.wrapper.serial.CliSerializationConfig
import io.foxcapades.lib.cli.wrapper.serial.values.FlagPredicate
import io.foxcapades.lib.cli.wrapper.serial.values.unsafeCast
import io.foxcapades.lib.cli.wrapper.util.BUG
import kotlin.reflect.KCallable
import kotlin.reflect.KClass

internal class AnnotatedFlag<T : Any>(
  override val type: KClass<out T>,
  override val instance: T,
  override val accessor: KCallable<Any?>,
  override val annotation: CliFlagAnnotation,
  private val delegate: Flag<Argument<Any?>, Any?>,
)
  : ResolvedFlag<T, Any?>
  , AnnotatedValueAccessorReference<T, Any?, KCallable<Any?>, CliFlagAnnotation>
{
  private inline val filter: FlagPredicate<Flag<Argument<Any?>, Any?>, Argument<Any?>, Any?>
    get() = annotation.filter.getOrCreate().unsafeCast()

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

  override val argument
    get() = delegate.argument

  override val isRequired
    get() = annotation.required || delegate.isRequired

  override fun shouldSerialize(
    config:    CliSerializationConfig,
    reference: ValueAccessorReference<*, Any?, out KCallable<Any?>>,
  ) =
    if (annotation.hasFilter)
      filter.shouldInclude(delegate, this, config)
    else
      delegate.shouldSerialize(config, reference)

  override fun writeToString(builder: CliFlagWriter<*, Any?>) {
    if (delegate is BooleanFlag && delegate.isToggleFlag) {

      // Filter out flags that are not set, or are set to false.
      // TODO: this should be the job of the toggle flag
      if (!delegate.isSet || !delegate.argument.get())
        return

      builder.writePreferredForm()
    } else {
      val argWriter = builder.writePreferredForm()

      if (delegate.argument.shouldSerialize(builder.config, builder.reference))
        argument.writeToString(argWriter)
    }
  }
}
