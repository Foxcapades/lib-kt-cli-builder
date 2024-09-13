package io.foxcapades.lib.cli.builder.flag.ref.impl

import io.foxcapades.lib.cli.builder.arg.ref.impl.FauxArgument
import io.foxcapades.lib.cli.builder.command.ref.ResolvedCommand
import io.foxcapades.lib.cli.builder.flag.CliFlag
import io.foxcapades.lib.cli.builder.flag.CliFlagAnnotation
import io.foxcapades.lib.cli.builder.flag.filter.unsafeCast
import io.foxcapades.lib.cli.builder.flag.ref.ResolvedFlag
import io.foxcapades.lib.cli.builder.serial.CliFlagWriter
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.serial.writeArgument
import io.foxcapades.lib.cli.builder.util.values.ValueAccessor
import io.foxcapades.lib.cli.builder.util.values.ValueSource

internal class FauxFlag<V>(
  annotation:      CliFlagAnnotation,
  parentComponent: ResolvedCommand<*>,
  accessor:        ValueAccessor<V>,
)
  : ResolvedFlag<V>
{
  private val annotation: CliFlagAnnotation = annotation

  override val parentComponent = parentComponent

  override val valueSource = accessor

  override val argument = FauxArgument(annotation.argument, this, accessor)

  override val hasLongForm
    get() = annotation.hasLongForm

  override val longForm
    get() = annotation.longForm

  override val hasShortForm
    get() = annotation.hasShortForm

  override val shortForm: Char
    get() = annotation.shortForm

  override val isRequired
    get() = annotation.required == CliFlag.Toggle.Yes

  override fun shouldSerialize(config: CliSerializationConfig, source: ValueSource) =
    if (annotation.hasFilter)
      annotation.initFilter().unsafeCast<V>().shouldInclude(this, config, source)
    else
      argument.shouldSerialize(config, source)

  override fun writeToString(writer: CliFlagWriter<*, V>) =
    writer.writePreferredForm().writeArgument(argument)
}
