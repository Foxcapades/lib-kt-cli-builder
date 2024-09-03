package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.flag.FauxFlag
import io.foxcapades.lib.cli.wrapper.reflect.shouldQuote
import io.foxcapades.lib.cli.wrapper.serial.CliArgumentAppender
import io.foxcapades.lib.cli.wrapper.serial.CliSerializationConfig
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.util.BUG
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

@JvmInline
internal value class FauxArgument<T : Any>(private val flag: FauxFlag<T>) : Argument<Any?> {
  override val isSet
    get() = flag.isSet

  override val default
    get() = BUG()

  override val hasDefault
    get() = BUG()

  override fun isDefault(config: CliSerializationConfig) = flag.isDefault(config)

  override val shouldQuote: Boolean
    get() = when (val t = flag.property.returnType.classifier) {
      is KClass<*> -> t.shouldQuote()
      else         -> true
    }

  override fun get() = flag.get()

  @Suppress("UNCHECKED_CAST")
  override fun writeToString(builder: CliArgumentAppender) {
    val formatter = try {
      flag.annotation.formatter.objectInstance ?: flag.annotation.formatter.createInstance()
    } catch (e: IllegalArgumentException) {
      // TODO: this should be its own exception type!
      throw IllegalStateException("could not instantiate value formatter: " + e.message)
    } as ArgumentFormatter<Any?>

    try { formatter(get(), builder) }
    catch (e: NullPointerException) { builder.config.nullSerializer(builder) }
  }

  override fun set(value: Any?) = BUG()
  override fun unset() = BUG()
}
