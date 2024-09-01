package io.foxcapades.lib.cli.wrapper.impl.arg

import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.impl.flag.FauxFlag
import io.foxcapades.lib.cli.wrapper.serial.CliStringBuilder
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter
import io.foxcapades.lib.cli.wrapper.utils.BUG
import kotlin.reflect.full.createInstance

@JvmInline
internal value class FauxArgument<T : Any>(private val flag: FauxFlag<T>) : Argument<Any?> {
  override val default
    get() = BUG()

  override val hasDefault
    get() = BUG()

  override val isDefault
    get() = BUG()

  override val isSet
    get() = BUG()

  override fun get() = flag.get()

  @Suppress("UNCHECKED_CAST")
  override fun writeToString(builder: CliStringBuilder) {
    val formatter = try {
      flag.annotation.formatter.objectInstance ?: flag.annotation.formatter.createInstance()
    } catch (e: IllegalArgumentException) {
      // TODO: this should be its own exception type!
      throw IllegalStateException("could not instantiate value formatter: " + e.message)
    } as ValueFormatter<Any?>

    val formatted = try { formatter(get(), builder.config) }
    catch (e: NullPointerException) { builder.config.nullSerializer(builder.config) }

    builder.append(formatted)
  }

  override fun set(value: Any?) = BUG()
  override fun unset() = BUG()
}