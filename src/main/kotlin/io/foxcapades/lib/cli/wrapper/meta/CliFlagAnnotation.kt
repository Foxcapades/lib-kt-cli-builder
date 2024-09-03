package io.foxcapades.lib.cli.wrapper.meta

@JvmInline
internal value class CliFlagAnnotation(val annotation: CliFlag) : CliComponentAnnotation {
  val hasLongForm
    get() = annotation.hasLongForm

  val longForm
    get() = annotation.longForm

  val hasShortForm
    get() = annotation.hasShortForm

  val shortForm
    get() = annotation.shortForm

  override val required
    get() = annotation.required

  override val defaultValueTest
    get() = annotation.defaultValueTest

  override val default
    get() = annotation.default

  override val includeDefault
    get() = annotation.includeDefault

  override val formatter
    get() = annotation.formatter
}
