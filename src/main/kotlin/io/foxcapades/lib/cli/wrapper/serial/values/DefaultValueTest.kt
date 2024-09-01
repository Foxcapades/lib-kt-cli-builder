package io.foxcapades.lib.cli.wrapper.serial.values

import io.foxcapades.lib.cli.wrapper.meta.CliFlag
import kotlin.reflect.KProperty1

fun interface DefaultValueTest {
  fun testValue(value: Any?, annotation: CliFlag, prop: KProperty1<*, *>): Boolean
}
