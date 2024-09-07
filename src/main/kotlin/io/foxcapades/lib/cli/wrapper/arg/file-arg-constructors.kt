package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.ArgOptions
import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.NullableArgOptions
import io.foxcapades.lib.cli.wrapper.util.*
import java.io.File

fun fileArg(action: ArgOptions<File>.() -> Unit): FileArgument =
  FileArgumentImpl(ArgOptions(File::class).also(action))

fun nullableFileArg(action: NullableArgOptions<File>.() -> Unit): Argument<File?> =
  GeneralArgumentImpl.of(NullableArgOptions(File::class).also {
    it.action()
    NullableArgOptions<File>::shouldQuote.property<Boolean>(it).setIfAbsent(true)
  })

