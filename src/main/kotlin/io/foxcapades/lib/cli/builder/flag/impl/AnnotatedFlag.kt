package io.foxcapades.lib.cli.builder.flag.impl

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.flag.BooleanFlag
import io.foxcapades.lib.cli.builder.flag.CliFlagAnnotation
import io.foxcapades.lib.cli.builder.flag.Flag
import io.foxcapades.lib.cli.builder.flag.ResolvedFlag
import io.foxcapades.lib.cli.builder.flag.filter.FlagPredicate
import io.foxcapades.lib.cli.builder.flag.filter.unsafeCast
import io.foxcapades.lib.cli.builder.reflect.AnnotatedValueAccessorReference
import io.foxcapades.lib.cli.builder.reflect.ValueAccessorReference
import io.foxcapades.lib.cli.builder.reflect.getOrCreate
import io.foxcapades.lib.cli.builder.serial.CliFlagWriter
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.BUG
import kotlin.reflect.KCallable
import kotlin.reflect.KClass

internal class AnnotatedFlag<T : Any>(
  override val type:       KClass<out T>,
  override val instance:   T,
  override val accessor:   KCallable<Any?>,
  override val annotation: CliFlagAnnotation,
  private val delegate:    Flag<Argument<Any?>, Any?>,
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
