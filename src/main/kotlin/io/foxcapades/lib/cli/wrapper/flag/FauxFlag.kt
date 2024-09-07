package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.ResolvedFlag
import io.foxcapades.lib.cli.wrapper.arg.FauxArgument
import io.foxcapades.lib.cli.wrapper.meta.CliFlagAnnotation
import io.foxcapades.lib.cli.wrapper.reflect.AnnotatedValueAccessorReference
import io.foxcapades.lib.cli.wrapper.serial.CliFlagWriter
import io.foxcapades.lib.cli.wrapper.serial.writeArgument
import kotlin.reflect.KCallable
import kotlin.reflect.KClass

internal class FauxFlag<T : Any>(
  override val instance: T,
  override val annotation: CliFlagAnnotation,
  override val accessor: KCallable<Any?>,
  override val type: KClass<out T>,
)
  : ResolvedFlag<T, Any?>
  , AnnotatedValueAccessorReference<T, Any?, KCallable<Any?>, CliFlagAnnotation>
{
  override val hasLongForm
    get() = annotation.hasLongForm

  override val longForm
    get() = annotation.longForm

  override val hasShortForm
    get() = annotation.hasShortForm

  override val shortForm
    get() = annotation.shortForm

  override val isRequired
    get() = annotation.required

  override val argument = FauxArgument(instance, this)

  override fun writeToString(builder: CliFlagWriter<*, Any?>) {
    // TODO: handle optional arguments
    builder.writePreferredForm().writeArgument(argument)
  }
}
