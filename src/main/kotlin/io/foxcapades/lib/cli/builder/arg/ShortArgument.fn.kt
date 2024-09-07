package io.foxcapades.lib.cli.builder.arg

import io.foxcapades.lib.cli.builder.arg.impl.ShortArgumentImpl
import io.foxcapades.lib.cli.builder.arg.impl.UniversalArgumentImpl
import io.foxcapades.lib.cli.builder.reflect.property
import io.foxcapades.lib.cli.builder.util.*
import io.foxcapades.lib.cli.builder.util.properties.setIfAbsent

fun shortArg(action: ArgOptions<Short>.() -> Unit = {}): ShortArgument =
  ShortArgumentImpl(ArgOptions(Short::class).also(action))

fun nullableShortArg(action: NullableArgOptions<Short>.() -> Unit = {}): Argument<Short?> {
  val opts = NullableArgOptions(Short::class).also(action)

  return UniversalArgumentImpl(
    type        = Short::class,
    nullable    = true,
    default     = NullableArgOptions<Short>::default.property(opts),
    shouldQuote = NullableArgOptions<Short>::shouldQuote.property<Boolean>(opts).setIfAbsent(false),
    isRequired  = NullableArgOptions<Short>::required.property(opts),
    formatter   = NullableArgOptions<Short>::formatter.property(opts),
    filter      = NullableArgOptions<Short>::filter.property(opts),
  )
}
