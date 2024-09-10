package io.foxcapades.lib.cli.builder.flag.ref.impl

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.command.ref.ResolvedCommand
import io.foxcapades.lib.cli.builder.flag.CliFlag
import io.foxcapades.lib.cli.builder.flag.Flag
import io.foxcapades.lib.cli.builder.flag.impl.CliFlagAnnotationImpl
import io.foxcapades.lib.cli.builder.flag.ref.ResolvedDelegateFlagRef
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.reflect.ValueAccessorReference
import kotlin.reflect.KCallable

internal class AnnotatedDelegateFlag<T : Any, V>(
  annotation: CliFlagAnnotationImpl,
  parent:    ResolvedCommand<T>,
  delegate:  Flag<Argument<V>, V>,
  accessor:  KCallable<V>,
)
  : ResolvedDelegateFlagRef<T, V>
  , DelegateFlag<T, V>(parent, delegate, accessor)
{
  private val annotation = annotation

  override val hasLongForm
    get() = annotation.hasLongForm || super.hasLongForm

  override val longForm
    get() = if (annotation.hasLongForm) annotation.longForm else super.longForm

  override val hasShortForm
    get() = annotation.hasShortForm || super.hasShortForm

  override val shortForm
    get() = if (annotation.hasShortForm) annotation.shortForm else annotation.shortForm

  override val isRequired
    get() = when (annotation.required) {
      CliFlag.Toggle.Yes   -> true
      CliFlag.Toggle.No    -> false
      CliFlag.Toggle.Unset -> super.isRequired
    }

  override fun shouldSerialize(
    config: CliSerializationConfig,
    reference: ValueAccessorReference<*, V, KCallable<V>>?,
  ) = if (annotation.hasFilter)
    annotation.initFilter<V>().shouldInclude(this, reference, config)
  else
    super<DelegateFlag>.shouldSerialize(config, reference)
}
