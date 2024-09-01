package io.foxcapades.lib.cli.wrapper.impl.arg

import io.foxcapades.lib.cli.wrapper.arg.ScalarArgument
import io.foxcapades.lib.cli.wrapper.serial.CliStringBuilder
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter

internal abstract class AbstractScalarArgument<T>(
  protected var value: T,
  override val default: T,
  hasDefault: Boolean,
  protected val formatter: ValueFormatter<T>,
) : AbstractArgument<T>(hasDefault), ScalarArgument<T> {
  override fun safeGet() = value

  override fun safeSet(value: T) {
    this.value = value
  }

  override fun safeUnset() = Unit

  override fun writeToString(builder: CliStringBuilder) {
    builder.append(formatter(value, builder.config))
  }
}