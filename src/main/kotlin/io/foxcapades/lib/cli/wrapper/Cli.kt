package io.foxcapades.lib.cli.wrapper

import io.foxcapades.lib.cli.wrapper.serial.CliSerializationConfig
import io.foxcapades.lib.cli.wrapper.serial.CommandSerializerImpl

object Cli {
  @JvmStatic
  @JvmOverloads
  fun <T : Any> toCliString(cliConfig: T, options: CliSerializationConfig = CliSerializationConfig()) =
    CommandSerializerImpl(cliConfig, options).serializeToString()

  @JvmStatic
  @JvmOverloads
  fun <T : Any> toCliCallArray(cliConfig: T, options: CliSerializationConfig = CliSerializationConfig()) =
    CommandSerializerImpl(cliConfig, options).serializeToArray()

  @JvmStatic
  @JvmOverloads
  fun <T : Any> toCliCallList(cliConfig: T, options: CliSerializationConfig = CliSerializationConfig()) =
    CommandSerializerImpl(cliConfig, options).serializeToList()

  @JvmStatic
  @JvmOverloads
  fun <T : Any> toProcessBuilder(cliConfig: T, options: CliSerializationConfig = CliSerializationConfig()) =
    ProcessBuilder(CommandSerializerImpl(cliConfig, options).serializeToList())
}


