package io.foxcapades.lib.cli.builder.arg

import io.foxcapades.lib.cli.builder.arg.impl.FileArgumentImpl
import io.foxcapades.lib.cli.builder.arg.impl.UniversalArgumentImpl
import io.foxcapades.lib.cli.builder.reflect.property
import io.foxcapades.lib.cli.builder.util.*
import io.foxcapades.lib.cli.builder.util.properties.setIfAbsent
import java.io.File

fun fileArg(action: ArgOptions<File>.() -> Unit): FileArgument =
  FileArgumentImpl(ArgOptions(File::class).also(action))

fun nullableFileArg(action: NullableArgOptions<File>.() -> Unit): Argument<File?> =
  UniversalArgumentImpl.of(NullableArgOptions(File::class).also {
    it.action()
    NullableArgOptions<File>::shouldQuote.property<Boolean>(it).setIfAbsent(true)
  })

