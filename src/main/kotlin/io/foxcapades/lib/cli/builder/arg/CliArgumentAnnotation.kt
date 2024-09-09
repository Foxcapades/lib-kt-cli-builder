package io.foxcapades.lib.cli.builder.arg

import io.foxcapades.lib.cli.builder.arg.filter.UnconfiguredArgFilter
import io.foxcapades.lib.cli.builder.arg.format.UnconfiguredArgFormatter
import io.foxcapades.lib.cli.builder.component.CliComponentAnnotation

@JvmInline
internal value class CliArgumentAnnotation(val annotation: CliArgument) : CliComponentAnnotation {
  /**
   * Indicates whether the annotation instance was marked as being required.
   */
  inline val required
    get() = annotation.required

  inline val hasFilter
    get() = annotation.inclusionTest != UnconfiguredArgFilter::class

  inline val filter
    get() = annotation.inclusionTest

  inline val shouldQuote
    get() = annotation.shouldQuote

  inline val hasFormatter
    get() = annotation.formatter != UnconfiguredArgFormatter::class

  inline val formatter
    get() = annotation.formatter
}
