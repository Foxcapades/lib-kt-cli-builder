package io.foxcapades.lib.cli.builder.arg

import io.foxcapades.lib.cli.builder.arg.impl.StringArgumentImpl
import io.foxcapades.lib.cli.builder.arg.impl.UniversalArgumentImpl
import io.foxcapades.lib.cli.builder.reflect.property
import io.foxcapades.lib.cli.builder.util.*
import io.foxcapades.lib.cli.builder.util.properties.setIfAbsent

fun stringArg(action: ArgOptions<String>.() -> Unit): StringArgument =
  StringArgumentImpl(ArgOptions(String::class).also(action))

fun nullableStringArg(action: NullableArgOptions<String>.() -> Unit): Argument<String?> =
  UniversalArgumentImpl.of(NullableArgOptions(String::class).also {
    it.action()
    NullableArgOptions<String>::shouldQuote.property<Boolean>(it).setIfAbsent(true)
  })
