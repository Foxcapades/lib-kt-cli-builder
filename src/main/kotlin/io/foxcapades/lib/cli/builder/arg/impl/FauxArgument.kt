package io.foxcapades.lib.cli.builder.arg.impl

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.arg.CliArgument
import io.foxcapades.lib.cli.builder.arg.CliArgumentAnnotation
import io.foxcapades.lib.cli.builder.arg.filter.forceAny
import io.foxcapades.lib.cli.builder.arg.format.forceAny
import io.foxcapades.lib.cli.builder.component.CliComponentAnnotation
import io.foxcapades.lib.cli.builder.flag.CliFlagAnnotation
import io.foxcapades.lib.cli.builder.reflect.AnnotatedValueAccessorReference
import io.foxcapades.lib.cli.builder.reflect.ValueAccessorReference
import io.foxcapades.lib.cli.builder.reflect.getOrCreate
import io.foxcapades.lib.cli.builder.reflect.shouldQuote
import io.foxcapades.lib.cli.builder.serial.CliArgumentWriter
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.BUG
import io.foxcapades.lib.cli.builder.util.properties.NoSuchDefaultValueException
import kotlin.reflect.KCallable

internal class FauxArgument<T : Any>(
  val instance: T,
  val reference: AnnotatedValueAccessorReference<T, Any?, KCallable<Any?>, out CliComponentAnnotation>,
) : Argument<Any?> {
  inline val annotation get() = when (val a = reference.annotation) {
    is CliFlagAnnotation     -> a.argument
    is CliArgumentAnnotation -> a.annotation
    else                     -> BUG()
  }

  override val isSet
    get() = reference.isNullable && get() != null

  override val hasDefault
    get() = BUG()

  override val isRequired
    get() = annotation.required

  override val shouldQuote: Boolean
    get() = when (annotation.shouldQuote) {
      CliArgument.ShouldQuote.Auto -> reference.type.shouldQuote()
      CliArgument.ShouldQuote.Yes  -> true
      CliArgument.ShouldQuote.No   -> false
    }

  override fun getDefault() = throw NoSuchDefaultValueException()

  override fun get() = reference.getValue(instance)

  override fun set(value: Any?) = BUG()

  override fun unset() = BUG()

  override fun shouldSerialize(
    config:    CliSerializationConfig,
    reference: ValueAccessorReference<*, Any?, out KCallable<Any?>>,
  ) =
    annotation.inclusionTest.getOrCreate().forceAny().shouldInclude(this, reference, config)

  override fun writeToString(writer: CliArgumentWriter<*, Any?>) {
    val formatter = annotation.formatter.getOrCreate().forceAny()

    try {
      formatter.formatValue(get(), writer)
    } catch (e: NullPointerException) {
      writer.config.nullSerializer.formatValue(writer)
    }
  }
}
