package io.foxcapades.lib.cli.builder.flag.ref.impl

import io.foxcapades.lib.cli.builder.arg.ref.impl.FauxArgument
import io.foxcapades.lib.cli.builder.command.ref.ResolvedCommand
import io.foxcapades.lib.cli.builder.flag.CliFlag
import io.foxcapades.lib.cli.builder.flag.impl.CliFlagAnnotationImpl
import io.foxcapades.lib.cli.builder.flag.ref.ResolvedImaginaryFlag
import io.foxcapades.lib.cli.builder.serial.CliFlagWriter
import io.foxcapades.lib.cli.builder.serial.writeArgument
import kotlin.reflect.KCallable

internal class FauxFlag<T : Any, V>(
  annotation: CliFlagAnnotationImpl,
  parent:     ResolvedCommand<T>,
  accessor:   KCallable<V>,
) : ResolvedImaginaryFlag<T, V> {
  override val annotation = annotation

  override val parentComponent = parent

  override val accessor = accessor

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

  override val argument by lazy { FauxArgument<T, V>(annotation.argument, parent.instance, this, accessor) }

  override fun writeToString(writer: CliFlagWriter<*, V>) {
    val name = if (accessor.name.startsWith("get"))
      accessor.name.substring(3)
    else
      accessor.name

    writer.writePreferredForm().writeArgument(argument)

    writer.config.propertyNameFormatter.format(name, writer.config)
    TODO("Not yet implemented")
  }
}
