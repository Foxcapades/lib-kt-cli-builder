package io.foxcapades.lib.cli.builder.flag.ref.impl

import io.foxcapades.lib.cli.builder.arg.ref.impl.AnnotatedValueArgument
import io.foxcapades.lib.cli.builder.command.ref.ResolvedCommand
import io.foxcapades.lib.cli.builder.flag.CliFlag
import io.foxcapades.lib.cli.builder.flag.CliFlagAnnotation
import io.foxcapades.lib.cli.builder.flag.Flag
import io.foxcapades.lib.cli.builder.flag.filter.unsafeCast
import io.foxcapades.lib.cli.builder.flag.ref.ResolvedFlag
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.values.ValueAccessor
import io.foxcapades.lib.cli.builder.util.values.ValueSource

internal class AnnotatedDelegateFlag<T : Any, V>(
  annotation: CliFlagAnnotation,
  parent:     ResolvedCommand<T>,
  delegate:   Flag<V>,
  source: ValueAccessor<V>,
)
  : ResolvedFlag<V>
  , AbstractValueFlag<T, V>(parent, delegate, source)
{
  private val annotation = annotation

  override val valueSource = source

  override val argument = AnnotatedValueArgument(annotation.argument, this, delegate.argument, source)

  override val parentComponent: ResolvedCommand<T>
    get() = super.parentComponent

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

  override fun shouldSerialize(config: CliSerializationConfig, source: ValueSource) =
    if (annotation.hasFilter)
      annotation.initFilter().unsafeCast<V>().shouldInclude(this, config, source)
    else
      super.shouldSerialize(config, source)
}
