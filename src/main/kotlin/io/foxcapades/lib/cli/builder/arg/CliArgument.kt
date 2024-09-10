package io.foxcapades.lib.cli.builder.arg

import io.foxcapades.lib.cli.builder.arg.filter.ArgumentPredicate
import io.foxcapades.lib.cli.builder.arg.filter.UnconfiguredArgFilter
import io.foxcapades.lib.cli.builder.arg.format.ArgumentFormatter
import io.foxcapades.lib.cli.builder.arg.format.UnconfiguredArgFormatter
import kotlin.reflect.KClass

annotation class CliArgument(
  val required: Toggle = Toggle.Unset,

  val inclusionTest: KClass<out ArgumentPredicate<*, *>> = UnconfiguredArgFilter::class,

  val shouldQuote: Toggle = Toggle.Unset,

  val formatter: KClass<out ArgumentFormatter<*>> = UnconfiguredArgFormatter::class
) {
  enum class Toggle { Yes, No, Unset }
}
