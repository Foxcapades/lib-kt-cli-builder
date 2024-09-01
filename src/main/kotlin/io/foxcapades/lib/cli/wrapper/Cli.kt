package io.foxcapades.lib.cli.wrapper

import io.foxcapades.lib.cli.wrapper.impl.CliBuilder
import io.foxcapades.lib.cli.wrapper.serial.CliSerializationConfig

object Cli {
  @JvmStatic
  @JvmOverloads
  fun <T : Any> toCliString(cliConfig: T, options: CliSerializationConfig = CliSerializationConfig()) =
    CliBuilder(cliConfig, options).asString()

  @JvmStatic
  @JvmOverloads
  fun <T : Any> toCliCallArray(cliConfig: T, options: CliSerializationConfig = CliSerializationConfig()) =
    CliBuilder(cliConfig, options).asList().toTypedArray()

  @JvmStatic
  @JvmOverloads
  fun <T : Any> toCliCallList(cliConfig: T, options: CliSerializationConfig = CliSerializationConfig()) =
    CliBuilder(cliConfig, options).asList()
}


