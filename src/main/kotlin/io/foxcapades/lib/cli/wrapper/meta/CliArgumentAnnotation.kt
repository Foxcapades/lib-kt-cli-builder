package io.foxcapades.lib.cli.wrapper.meta

@JvmInline
internal value class CliArgumentAnnotation(val annotation: CliArgument) : CliComponentAnnotation {
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
