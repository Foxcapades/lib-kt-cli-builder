package io.foxcapades.lib.cli.builder.arg

import io.foxcapades.lib.cli.builder.arg.filter.ArgumentPredicate
import io.foxcapades.lib.cli.builder.arg.filter.UnconfiguredArgFilter
import io.foxcapades.lib.cli.builder.arg.format.ArgumentFormatter
import io.foxcapades.lib.cli.builder.arg.format.UnconfiguredArgFormatter
import kotlin.reflect.KClass

annotation class CliArgument(
  val required: Boolean = false,

  val inclusionTest: KClass<out ArgumentPredicate<*, *>> = UnconfiguredArgFilter::class,

  val shouldQuote: ShouldQuote = ShouldQuote.Auto,

  val formatter: KClass<out ArgumentFormatter<*>> = UnconfiguredArgFormatter::class
) {
  enum class ShouldQuote { Yes, No, Auto }
}
