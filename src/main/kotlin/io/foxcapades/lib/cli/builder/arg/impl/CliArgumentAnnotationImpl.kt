package io.foxcapades.lib.cli.builder.arg.impl

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.arg.CliArgument
import io.foxcapades.lib.cli.builder.arg.CliArgumentAnnotation
import io.foxcapades.lib.cli.builder.arg.filter.ArgumentPredicate
import io.foxcapades.lib.cli.builder.arg.filter.UnconfiguredArgFilter
import io.foxcapades.lib.cli.builder.arg.filter.unsafeCast
import io.foxcapades.lib.cli.builder.arg.format.ArgumentFormatter
import io.foxcapades.lib.cli.builder.arg.format.UnconfiguredArgFormatter
import io.foxcapades.lib.cli.builder.arg.format.unsafeCast
import io.foxcapades.lib.cli.builder.util.reflect.getOrCreate

@JvmInline
internal value class CliArgumentAnnotationImpl(override val annotation: CliArgument) : CliArgumentAnnotation {
  override val required
    get() = annotation.required

  override val shouldQuote
    get() = annotation.shouldQuote

  override val hasFilter
    get() = annotation.inclusionTest != UnconfiguredArgFilter::class

  override val filter
    get() = annotation.inclusionTest

  override val hasFormatter
    get() = annotation.formatter != UnconfiguredArgFormatter::class

  override val formatter
    get() = annotation.formatter

  fun <V> initFilter(): ArgumentPredicate<Argument<V>, V> = filter.getOrCreate().unsafeCast()

  fun <V> initFormatter(): ArgumentFormatter<V> = formatter.getOrCreate().unsafeCast()
}
