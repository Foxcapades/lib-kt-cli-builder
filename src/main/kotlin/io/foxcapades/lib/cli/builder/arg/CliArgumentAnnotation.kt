package io.foxcapades.lib.cli.builder.arg

import io.foxcapades.lib.cli.builder.arg.filter.ArgumentPredicate
import io.foxcapades.lib.cli.builder.arg.format.ArgumentFormatter
import io.foxcapades.lib.cli.builder.component.CliComponentAnnotation
import kotlin.reflect.KClass

interface CliArgumentAnnotation : CliComponentAnnotation<CliArgument> {
  /**
   * Indicates whether the annotation instance was marked as being required.
   */
  val required: CliArgument.Toggle
  val shouldQuote: CliArgument.Toggle
  val hasFilter: Boolean
  val filter: KClass<out ArgumentPredicate<*, *>>
  val hasFormatter: Boolean
  val formatter: KClass<out ArgumentFormatter<*>>
}
