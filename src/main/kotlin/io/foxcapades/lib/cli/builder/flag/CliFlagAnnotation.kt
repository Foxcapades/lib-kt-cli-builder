package io.foxcapades.lib.cli.builder.flag

import io.foxcapades.lib.cli.builder.component.CliComponentAnnotation
import io.foxcapades.lib.cli.builder.flag.filter.UnconfiguredFlagFilter

@JvmInline
internal value class CliFlagAnnotation(val annotation: CliFlag) : CliComponentAnnotation {
  /**
   * Indicates whether the annotation instance was marked as being required.
   */
  inline val required
    get() = annotation.required

  inline val hasLongForm
    get() = annotation.hasLongForm

  inline val longForm
    get() = annotation.longForm

  inline val hasShortForm
    get() = annotation.hasShortForm

  inline val shortForm
    get() = annotation.shortForm

  inline val hasFilter
    get() = annotation.inclusionTest != UnconfiguredFlagFilter::class

  inline val filter
    get() = annotation.inclusionTest

  inline val argument
    get() = annotation.argument
}
