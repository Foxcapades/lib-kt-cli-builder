package io.foxcapades.lib.cli.builder.flag.impl

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.arg.impl.CliArgumentAnnotationImpl
import io.foxcapades.lib.cli.builder.flag.*
import io.foxcapades.lib.cli.builder.flag.filter.FlagPredicate
import io.foxcapades.lib.cli.builder.flag.filter.UnconfiguredFlagFilter
import io.foxcapades.lib.cli.builder.flag.filter.unsafeCast
import io.foxcapades.lib.cli.builder.util.reflect.getOrCreate

@JvmInline
internal value class CliFlagAnnotationImpl(override val annotation: CliFlag) : CliFlagAnnotation {
  override val required
    get() = annotation.required

  //

  override val hasLongForm
    get() = annotation.hasLongForm

  override val longForm
    get() = annotation.longForm

  //

  override val hasShortForm
    get() = annotation.hasShortForm

  override val shortForm
    get() = annotation.shortForm

  //

  override val hasFilter
    get() = annotation.inclusionTest != UnconfiguredFlagFilter::class

  override val filter
    get() = annotation.inclusionTest

  //

  override val argument
    get() = CliArgumentAnnotationImpl(annotation.argument)

  //

  fun <V> initFilter(): FlagPredicate<Flag<Argument<V>, V>, Argument<V>, V> = filter.getOrCreate().unsafeCast()
}
