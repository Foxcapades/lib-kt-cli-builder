package io.foxcapades.lib.cli.wrapper.meta

import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.serial.values.ComponentDefaultTest
import kotlin.reflect.KClass

sealed interface CliComponentAnnotation {
  val required: Boolean

  val defaultValueTest: KClass<out ComponentDefaultTest<*, *>>

  val default: String

  val includeDefault: Boolean

  val formatter: KClass<out ArgumentFormatter<*>>
}
