package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.ResolvedComponent
import io.foxcapades.lib.cli.wrapper.meta.CliArgument
import io.foxcapades.lib.cli.wrapper.meta.CliArgumentAnnotation
import io.foxcapades.lib.cli.wrapper.meta.CliFlagAnnotation
import io.foxcapades.lib.cli.wrapper.reflect.AnnotatedPropertyReference
import io.foxcapades.lib.cli.wrapper.reflect.getOrCreate
import io.foxcapades.lib.cli.wrapper.reflect.shouldQuote
import io.foxcapades.lib.cli.wrapper.serial.CliArgumentAppender
import io.foxcapades.lib.cli.wrapper.serial.CliSerializationConfig
import io.foxcapades.lib.cli.wrapper.serial.values.forceAny
import io.foxcapades.lib.cli.wrapper.util.BUG
import io.foxcapades.lib.cli.wrapper.util.NoSuchDefaultValueException

internal class FauxArgument<T : Any>(
  val instance: T,
  val reference: AnnotatedPropertyReference<T, Any?, *>,
) : Argument<Any?> {
  inline val annotation get() = when (val a = reference.annotation) {
    is CliFlagAnnotation     -> a.argument
    is CliArgumentAnnotation -> a.annotation
  }

  override val isSet
    get() = reference.propertyIsNullable && get() != null

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

  override fun get() = reference.property.get(instance)

  override fun set(value: Any?) = BUG()

  override fun unset() = BUG()

  override fun shouldSerialize(config: CliSerializationConfig, reference: ResolvedComponent<*, Any?>) =
    annotation.inclusionTest.getOrCreate().forceAny().shouldInclude(this, reference, config)

  override fun writeToString(builder: CliArgumentAppender) {
    val formatter = annotation.formatter.getOrCreate().forceAny()

    try {
      formatter.formatValue(get(), builder)
    } catch (e: NullPointerException) {
      builder.config.nullSerializer.formatValue(builder)
    }
  }
}
