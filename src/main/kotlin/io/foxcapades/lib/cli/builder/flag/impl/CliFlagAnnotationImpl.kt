package io.foxcapades.lib.cli.builder.flag.impl

import io.foxcapades.lib.cli.builder.arg.impl.CliArgumentAnnotationImpl
import io.foxcapades.lib.cli.builder.flag.CliFlag
import io.foxcapades.lib.cli.builder.flag.CliFlagAnnotation
import io.foxcapades.lib.cli.builder.flag.filter.FlagPredicate
import io.foxcapades.lib.cli.builder.flag.filter.UnconfiguredFlagFilter
import io.foxcapades.lib.cli.builder.util.reflect.getOrCreate

@JvmInline
internal value class CliFlagAnnotationImpl(override val annotation: CliFlag) : CliFlagAnnotation {
  override val required
    get() = annotation.required

  //

  override val hasLongForm
    get() = annotation.longForm != ""

  override val longForm
    get() = annotation.longForm

  //

  override val hasShortForm
    get() = annotation.shortForm != '\u0000'

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

  override fun initFilter(): FlagPredicate<*> = filter.getOrCreate()
}
