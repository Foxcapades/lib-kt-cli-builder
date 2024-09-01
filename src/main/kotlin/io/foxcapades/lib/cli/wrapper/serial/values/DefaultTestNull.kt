package io.foxcapades.lib.cli.wrapper.serial.values

import io.foxcapades.lib.cli.wrapper.meta.CliFlag
import kotlin.reflect.KProperty1

// null
internal object DefaultTestNull : DefaultValueTest {
  override fun testValue(value: Any?, annotation: CliFlag, prop: KProperty1<*, *>) = value == null
}