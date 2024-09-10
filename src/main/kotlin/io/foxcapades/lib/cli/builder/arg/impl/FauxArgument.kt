package io.foxcapades.lib.cli.builder.arg.impl

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.arg.CliArgument
import io.foxcapades.lib.cli.builder.arg.ResolvedArgumentOld
import io.foxcapades.lib.cli.builder.arg.filter.unsafeCast
import io.foxcapades.lib.cli.builder.arg.format.unsafeCast
import io.foxcapades.lib.cli.builder.serial.CliArgumentWriter
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.BUG
import io.foxcapades.lib.cli.builder.util.properties.NoSuchDefaultValueException
import io.foxcapades.lib.cli.builder.util.reflect.AnnotatedValueAccessorReference
import io.foxcapades.lib.cli.builder.util.reflect.ValueAccessorReference
import io.foxcapades.lib.cli.builder.util.reflect.getOrCreate
import io.foxcapades.lib.cli.builder.util.reflect.shouldQuote
import kotlin.reflect.KCallable

/**
 * Represents a property or getter that has been annotated as being an argument.
 *
 * @param T Containing class type.
 *
 * @param V Argument value type.
 */
internal class FauxArgument<T : Any, V>(
  override val instance:   T,
  override val annotation: CliArgumentAnnotationImpl,
  private  val reference:  ValueAccessorReference<T, V, KCallable<V>>
)
  : ResolvedArgumentOld<T, V>
  , AnnotatedValueAccessorReference<T, V, KCallable<V>, CliArgumentAnnotationImpl>
  , ValueAccessorReference<T, V, KCallable<V>> by reference
{
  override val isSet
    get() = reference.isNullable && get() != null

  override val hasDefault
    get() = BUG()

  override val isRequired
    get() = annotation.required

  override val shouldQuote: Boolean
    get() = when (annotation.shouldQuote) {
      CliArgument.Toggle.Yes  -> true
      CliArgument.Toggle.No   -> false
      CliArgument.Toggle.Unset -> reference.containingType.shouldQuote()
    }

  override val qualifiedName: String
    get() = "argument " + reference.qualifiedName


  override fun getDefault() = throw NoSuchDefaultValueException()

  override fun get() = reference.getValue(instance)

  override fun set(value: V) = BUG()

  override fun unset() = BUG()

  override fun shouldSerialize(
    config:    CliSerializationConfig,
    reference: ValueAccessorReference<*, V, KCallable<V>>?,
  ) =
    annotation.filter.getOrCreate().unsafeCast<Argument<V>, V>().shouldInclude(this, config, reference)

  override fun writeToString(writer: CliArgumentWriter<*, V>) {
    val formatter = annotation.formatter.getOrCreate().unsafeCast<V>()

    try {
      formatter.formatValue(get(), writer)
    } catch (e: NullPointerException) {
      writer.config.nullSerializer.formatValue(writer)
    }
  }
}
