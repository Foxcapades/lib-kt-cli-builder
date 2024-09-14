package io.foxcapades.lib.cli.builder.flag.ref.impl

import io.foxcapades.lib.cli.builder.arg.ref.impl.AnnotatedValueArgument
import io.foxcapades.lib.cli.builder.command.ref.ResolvedCommand
import io.foxcapades.lib.cli.builder.flag.CliFlag
import io.foxcapades.lib.cli.builder.flag.CliFlagAnnotation
import io.foxcapades.lib.cli.builder.flag.Flag
import io.foxcapades.lib.cli.builder.flag.filter.unsafeCast
import io.foxcapades.lib.cli.builder.flag.ref.ResolvedFlag
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.reflect.getOrCreate
import io.foxcapades.lib.cli.builder.util.values.ValueSource

internal class AnnotatedValueFlag<T : Any, V>(
  annotation: CliFlagAnnotation,
  parent:     ResolvedCommand<T>,
  instance:   Flag<V>,
  source: ValueSource,
)
  : ResolvedFlag<V>
  , AbstractValueFlag<T, V>(parent, instance, source)
{
  private val annotation = annotation

  override val argument = AnnotatedValueArgument(annotation.argument, this, instance.argument, source)

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

  override fun shouldSerialize(config: CliSerializationConfig, source: ValueSource) =
    if (annotation.hasFilter)
      annotation.filter.getOrCreate().unsafeCast<V>().shouldInclude(this, config, source)
    else
      super.shouldSerialize(config, source)
}
