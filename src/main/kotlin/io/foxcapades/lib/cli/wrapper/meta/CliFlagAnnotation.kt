package io.foxcapades.lib.cli.wrapper.meta

import io.foxcapades.lib.cli.wrapper.serial.values.InvalidFlagFilter

@JvmInline
internal value class CliFlagAnnotation(val annotation: CliFlag) : CliComponentAnnotation {
  inline val hasLongForm
    get() = annotation.hasLongForm

  inline val longForm
    get() = annotation.longForm

  inline val hasShortForm
    get() = annotation.hasShortForm

  inline val shortForm
    get() = annotation.shortForm

  inline val hasFilter
    get() = annotation.inclusionTest != InvalidFlagFilter::class

  inline val filter
    get() = annotation.inclusionTest

  inline val argument
    get() = annotation.argument

  override val required
    get() = annotation.required
}
