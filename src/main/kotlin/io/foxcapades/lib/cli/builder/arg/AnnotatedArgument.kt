package io.foxcapades.lib.cli.builder.arg

import io.foxcapades.lib.cli.builder.arg.filter.unsafeCast
import io.foxcapades.lib.cli.builder.arg.format.unsafeCast
import io.foxcapades.lib.cli.builder.arg.impl.CliArgumentAnnotationImpl
import io.foxcapades.lib.cli.builder.serial.CliArgumentWriter
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.reflect.AnnotatedValueAccessorReference
import io.foxcapades.lib.cli.builder.util.reflect.ValueAccessorReference
import io.foxcapades.lib.cli.builder.util.reflect.getOrCreate
import kotlin.reflect.KCallable
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

/**
 * Represents an argument delegate property that also has a `CliArgument`
 * annotation attached.
 *
 * @param T Type of the argument delegate's containing class.
 *
 * @param V Argument value type.
 */
internal class AnnotatedArgument<T : Any, V>(
  override val containingType:       KClass<out T>,
  override val instance:   T,
  override val accessor:   KProperty1<T, V>,
  override val annotation: CliArgumentAnnotationImpl,
  private  val delegate:   Argument<V>,
)
  : ResolvedArgumentOld<T, V>
  , AnnotatedValueAccessorReference<T, V, KCallable<V>, CliArgumentAnnotationImpl>
{
  override val hasDefault
    get() = delegate.hasDefault

  override val isSet
    get() = delegate.isSet

  override val isRequired
    get() = annotation.required || delegate.isRequired

  override val shouldQuote by lazy { when (annotation.shouldQuote) {
    CliArgument.ShouldQuote.Yes  -> true
    CliArgument.ShouldQuote.No   -> false
    CliArgument.ShouldQuote.Auto -> delegate.shouldQuote
  } }

  override fun get() = delegate.get()

  override fun set(value: V) = delegate.set(value)

  override fun unset() = delegate.unset()

  override fun getDefault() = delegate.getDefault()

  override fun shouldSerialize(
    config:    CliSerializationConfig,
    reference: ValueAccessorReference<*, V, KCallable<V>>?,
  ) =
    if (annotation.hasFilter)
      annotation.filter.getOrCreate().unsafeCast<Argument<V>, V>().shouldInclude(delegate, config, this)
    else
      delegate.shouldSerialize(config, reference)

  override fun writeToString(writer: CliArgumentWriter<*, V>) =
    if (annotation.hasFormatter)
      annotation.formatter.getOrCreate().unsafeCast<V>().formatValue(get(), writer)
    else
      delegate.writeToString(writer)
}
