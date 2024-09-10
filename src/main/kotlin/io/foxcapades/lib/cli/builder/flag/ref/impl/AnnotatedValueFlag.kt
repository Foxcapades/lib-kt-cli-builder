package io.foxcapades.lib.cli.builder.flag.ref.impl

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.arg.impl.CliArgumentAnnotationImpl
import io.foxcapades.lib.cli.builder.arg.ref.ResolvedArgument
import io.foxcapades.lib.cli.builder.arg.ref.impl.AnnotatedFlagValueArgument
import io.foxcapades.lib.cli.builder.command.ref.ResolvedCommand
import io.foxcapades.lib.cli.builder.flag.CliFlag
import io.foxcapades.lib.cli.builder.flag.Flag
import io.foxcapades.lib.cli.builder.flag.filter.unsafeCast
import io.foxcapades.lib.cli.builder.flag.impl.CliFlagAnnotationImpl
import io.foxcapades.lib.cli.builder.flag.ref.ResolvedFlagValueRef
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.reflect.ValueAccessorReference
import io.foxcapades.lib.cli.builder.util.reflect.getOrCreate
import kotlin.reflect.KCallable

internal class AnnotatedValueFlag<T : Any, V>(
  annotation: CliFlagAnnotationImpl,
  parent:     ResolvedCommand<T>,
  instance:   Flag<Argument<V>, V>,
  accessor:   KCallable<Flag<Argument<V>, V>>,
)
  : ResolvedFlagValueRef<T, V>
  , ValueFlag<T, V>(parent, instance, accessor)
{
  private val annotation = annotation

  override val hasLongForm
    get() = annotation.hasLongForm || super.hasLongForm

  override val longForm
    get() = when {
      annotation.hasLongForm -> annotation.longForm
      else                   -> super.longForm
    }

  override val hasShortForm
    get() = annotation.hasShortForm || super.hasShortForm

  override val shortForm
    get() = when {
      annotation.hasShortForm -> annotation.shortForm
      else                    -> super.shortForm
    }

  override val isRequired
    get() = when (annotation.required) {
      CliFlag.Toggle.Yes   -> true
      CliFlag.Toggle.No    -> false
      CliFlag.Toggle.Unset -> super.isRequired
    }

  override val argument: ResolvedArgument<V> by lazy { AnnotatedFlagValueArgument(
    CliArgumentAnnotationImpl(annotation.argument),
    this,
    instance.argument,
  ) }

  override fun shouldSerialize(
    config: CliSerializationConfig,
    reference: ValueAccessorReference<*, V, KCallable<V>>?,
  ) = if (annotation.hasFilter)
    annotation.filter.getOrCreate().unsafeCast<Flag<Argument<V>, V>, Argument<V>, V>().shouldInclude(this, reference, config)
  else
    super<ValueFlag>.shouldSerialize(config, reference)
}
