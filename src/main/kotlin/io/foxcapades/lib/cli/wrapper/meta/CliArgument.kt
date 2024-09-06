package io.foxcapades.lib.cli.wrapper.meta

import io.foxcapades.lib.cli.wrapper.serial.NullableGeneralStringifier
import io.foxcapades.lib.cli.wrapper.serial.values.ArgSetFilter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentPredicate
import kotlin.reflect.KClass

annotation class CliArgument(
  val required: Boolean = false,

//  val default: String = CliComponentUnsetDefault,

  val inclusionTest: KClass<out ArgumentPredicate<*, *>> = ArgSetFilter::class,

  val shouldQuote: ShouldQuote = ShouldQuote.Auto,

  val formatter: KClass<out ArgumentFormatter<*>> = NullableGeneralStringifier::class,
) {
  enum class ShouldQuote { Yes, No, Auto }
}
