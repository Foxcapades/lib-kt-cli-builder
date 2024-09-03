package io.foxcapades.lib.cli.wrapper.meta

import io.foxcapades.lib.cli.wrapper.serial.NullableGeneralStringifier
import io.foxcapades.lib.cli.wrapper.serial.values.ArgDefaultTestReqDependent
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentDefaultTest
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import kotlin.reflect.KClass

annotation class CliArgument(
  val required: Boolean = false,

  val defaultValueTest: KClass<out ArgumentDefaultTest<*>> = ArgDefaultTestReqDependent::class,

  val default: String = CliComponentUnsetDefault,

  val includeDefault: Boolean = false,

  val formatter: KClass<out ArgumentFormatter<*>> = NullableGeneralStringifier::class,
)
