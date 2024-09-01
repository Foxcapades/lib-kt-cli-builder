package io.foxcapades.lib.cli.wrapper.impl.arg

import io.foxcapades.lib.cli.wrapper.UnsetArgumentDefaultException
import io.foxcapades.lib.cli.wrapper.arg.ComplexArgument
import io.foxcapades.lib.cli.wrapper.serial.CliSerializationConfig
import io.foxcapades.lib.cli.wrapper.serial.CliStringBuilder
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter

internal open class ComplexArgumentImpl<T> : AbstractArgument<T>, ComplexArgument<T> {
  private var value: T? = null
  private val unsafeDefault: T?
  private val formatter: ValueFormatter<T>

  constructor(unsafeDefault: T, formatter: ValueFormatter<T>) : super(true) {
    this.unsafeDefault = unsafeDefault
    this.formatter = formatter
  }

  constructor(formatter: ValueFormatter<T>) : super(false) {
    this.unsafeDefault = null
    this.formatter = formatter
  }

  override val default
    get() = if (hasDefault) unsafeDefault!! else throw UnsetArgumentDefaultException()

  override fun safeGet() = value!!

  override fun safeSet(value: T) {
    this.value = value
  }

  override fun safeUnset() {
    this.value = null
  }

  override fun writeToString(builder: CliStringBuilder) {
    builder.append(formatter(value!!, builder.config))
  }
}