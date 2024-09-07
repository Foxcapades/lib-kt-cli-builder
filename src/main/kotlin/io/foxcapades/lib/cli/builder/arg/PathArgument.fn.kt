package io.foxcapades.lib.cli.builder.arg

import io.foxcapades.lib.cli.builder.arg.impl.PathArgumentImpl
import io.foxcapades.lib.cli.builder.arg.impl.UniversalArgumentImpl
import io.foxcapades.lib.cli.builder.reflect.property
import io.foxcapades.lib.cli.builder.util.*
import io.foxcapades.lib.cli.builder.util.properties.setIfAbsent
import java.nio.file.Path

fun pathArg(action: ArgOptions<Path>.() -> Unit): PathArgument =
  PathArgumentImpl(ArgOptions(Path::class).also(action))

fun nullablePathArg(action: NullableArgOptions<Path>.() -> Unit): Argument<Path?> =
  UniversalArgumentImpl.of(NullableArgOptions(Path::class).also {
    it.action()
    NullableArgOptions<Path>::shouldQuote.property<Boolean>(it).setIfAbsent(true)
  })
