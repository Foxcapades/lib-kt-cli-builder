package io.foxcapades.lib.cli.builder.flag.impl

import io.foxcapades.lib.cli.builder.arg.CliArgumentAnnotation
import io.foxcapades.lib.cli.builder.arg.impl.FauxArgument
import io.foxcapades.lib.cli.builder.flag.CliFlagAnnotation
import io.foxcapades.lib.cli.builder.flag.ref.ResolvedDelegatedFlagRef
import io.foxcapades.lib.cli.builder.serial.CliFlagWriter
import io.foxcapades.lib.cli.builder.serial.writeArgument
import io.foxcapades.lib.cli.builder.util.reflect.AnnotatedValueAccessorReference
import io.foxcapades.lib.cli.builder.util.reflect.ValueAccessorReference
import kotlin.reflect.KCallable

/**
 * Represents a flag delegate property that also has a `CliFlag` annotation
 * attached.
 *
 * @param T Type of the flag delegate's containing class.
 *
 * @param V Flag's argument value type.
 */
internal class FauxFlag<T : Any, V>(
  override val instance:   T,
  override val annotation: CliFlagAnnotation,
  private val reference: ValueAccessorReference<T, V, KCallable<V>>,
)
  : ResolvedDelegatedFlagRef<T, V>
  , AnnotatedValueAccessorReference<T, V, KCallable<V>, CliFlagAnnotation>
  , ValueAccessorReference<T, V, KCallable<V>> by reference
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

  override val qualifiedName: String
    get() = "flag " + reference.qualifiedName

  override val argument = FauxArgument(instance, CliArgumentAnnotation(annotation.argument), this)

  override fun writeToString(builder: CliFlagWriter<*, V>) {
    // TODO: handle optional arguments
    builder.writePreferredForm().writeArgument(argument)
  }
}
