package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.UnsetArgumentDefaultException
import io.foxcapades.lib.cli.wrapper.serial.CliArgumentAppender
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter

internal open class ComplexArgumentImpl<T> : AbstractArgument<T>, ComplexArgument<T> {
  private val unsafeDefault: T?
  private val formatter: ArgumentFormatter<T>

  constructor(default: T, formatter: ArgumentFormatter<T>, shouldQuote: Boolean) : super(true, shouldQuote) {
    this.unsafeDefault = default
    this.formatter = formatter
  }

  constructor(default: T, formatter: ArgumentFormatter<T>) : super(true, true) {
    this.unsafeDefault = default
    this.formatter = formatter
  }

  constructor(formatter: ArgumentFormatter<T>, shouldQuote: Boolean) : super(false, shouldQuote) {
    this.unsafeDefault = null
    this.formatter = formatter
  }

  constructor(formatter: ArgumentFormatter<T>) : super(false, true) {
    this.unsafeDefault = null
    this.formatter = formatter
  }

  override val default
    get() = if (hasDefault) unsafeDefault!! else throw UnsetArgumentDefaultException()

  override fun writeToString(builder: CliArgumentAppender) {
    formatter(value.get(), builder)
  }
}