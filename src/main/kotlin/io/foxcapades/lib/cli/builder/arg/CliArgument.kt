package io.foxcapades.lib.cli.builder.arg

import io.foxcapades.lib.cli.builder.arg.filter.ArgSetFilter
import io.foxcapades.lib.cli.builder.arg.filter.ArgumentPredicate
import io.foxcapades.lib.cli.builder.arg.format.ArgumentFormatter
import io.foxcapades.lib.cli.builder.arg.format.NullableGeneralStringifier
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
