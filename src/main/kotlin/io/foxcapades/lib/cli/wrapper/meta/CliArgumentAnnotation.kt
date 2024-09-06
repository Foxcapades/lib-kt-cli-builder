package io.foxcapades.lib.cli.wrapper.meta

@JvmInline
internal value class CliArgumentAnnotation(val annotation: CliArgument) : CliComponentAnnotation {
  override val required
    get() = annotation.required

  inline val inclusionTest get() = annotation.inclusionTest

  inline val shouldQuote get() = annotation.shouldQuote

  inline val formatter get() = annotation.formatter
}
