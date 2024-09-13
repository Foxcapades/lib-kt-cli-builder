package io.foxcapades.lib.cli.builder.serial.impl

import io.foxcapades.lib.cli.builder.arg.ref.ResolvedArgument
import io.foxcapades.lib.cli.builder.arg.ref.forceAny
import io.foxcapades.lib.cli.builder.command.ref.ResolvedCommand
import io.foxcapades.lib.cli.builder.component.ResolvedComponent
import io.foxcapades.lib.cli.builder.flag.forceAny
import io.foxcapades.lib.cli.builder.flag.ref.ResolvedFlag
import io.foxcapades.lib.cli.builder.serial.CliArgumentWriter
import io.foxcapades.lib.cli.builder.serial.CliFlagWriter
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.BUG
import io.foxcapades.lib.cli.builder.util.dump
import io.foxcapades.lib.cli.builder.util.impl.StringBuilderAppender
import io.foxcapades.lib.cli.builder.util.logger

/**
 * @param T Top level CLI command config class containing components to be
 * serialized.
 */
internal class StringCliAppenderImpl<T : Any>(
  command:  ResolvedCommand<T>,
  config:   CliSerializationConfig,
  initSize: Int = 2048,
)
  : AbstractCliAppender<T>(config)
  , CliFlagWriter<T, Any?>
  , CliArgumentWriter<T, Any?>
{
  private val logger = logger()

  private val command = command

  private val buffer = StringBuilder(initSize)

  private val appender = StringBuilderAppender(buffer)

  private enum class Type { Long, Short, InQuotes, Arg, Raw }

  private lateinit var reference: ResolvedComponent

  private var last = Type.Raw

  private val components: Iterator<ResolvedComponent>

  @Suppress("UNCHECKED_CAST")
  override val flag
    get() = reference as ResolvedFlag<Any?>

  @Suppress("UNCHECKED_CAST")
  override val argument
    get() = reference as ResolvedArgument<Any?>

  init {
    val (name, it) = command.getCliCallComponents(config)
    buffer.append(name)
    components = it.iterator()
  }


  override fun writeLongForm(custom: String): CliArgumentWriter<T, Any?> {
    prepNext()
    buffer.append(config.longFlagPrefix).append(custom)
    last = Type.Long

    return this
  }

  override fun writeShortForm(custom: Char): CliArgumentWriter<T, Any?> {
    prepNext()
    buffer.append(config.shortFlagPrefix).append(custom)
    last = Type.Short

    return this
  }

  override fun rawWriteChar(char: Char) {
    prepNext()
    internalWriteChar(char)
    flush()
  }

  override fun rawWriteString(string: CharSequence) {
    prepNext()
    string.forEach(::internalWriteChar)
    flush()
  }

  override fun writeChar(char: Char) {
    maybeStartArg()
    internalWriteChar(char)
  }

  override fun writeString(string: CharSequence) {
    maybeStartArg()
    string.forEach(::internalWriteChar)
  }

  private fun maybeStartArg() {
    when (last) {
      Type.Long  -> buffer.append(config.longFlagValueSeparator)
      Type.Short -> buffer.append(config.shortFlagPrefix)
      Type.Raw   -> {}

      Type.InQuotes, Type.Arg -> return
    }

    val shouldQuote = when (reference) {
      is ResolvedFlag<*>     -> flag.argument.shouldQuote
      is ResolvedArgument<*> -> argument.shouldQuote
      else                   -> false
    }

    if (shouldQuote) {
      config.targetShell.startString(appender)
      last = Type.InQuotes
    } else {
      last = Type.Arg
    }
  }

  private fun internalWriteChar(char: Char) {
    if (config.targetShell.isArgumentSafe(char, last == Type.InQuotes))
      config.targetShell.escapeArgumentChar(char, last == Type.InQuotes, appender)
    else
      buffer.append(char)
  }

  override fun toString(): String {
    for (component in components) {
      reference = component

      when (component) {
        is ResolvedFlag<*>     -> component.forceAny().writeToString(this)
        is ResolvedArgument<*> -> component.forceAny().writeToString(this)
        else                   -> BUG()
      }
    }

    flush()

    return buffer.dump()
  }

  private fun prepNext() {
    flush()
    buffer.append(' ')
  }

  private fun flush() {
    if (last == Type.InQuotes)
      config.targetShell.endString(appender)

    last = Type.Raw
  }
}
