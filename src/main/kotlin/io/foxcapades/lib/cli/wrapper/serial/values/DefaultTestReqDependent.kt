package io.foxcapades.lib.cli.wrapper.serial.values

import io.foxcapades.lib.cli.wrapper.meta.CliFlag
import kotlin.reflect.KProperty1

internal object DefaultTestReqDependent : DefaultValueTest {
  override fun testValue(value: Any?, annotation: CliFlag, prop: KProperty1<*, *>) =
    if (annotation.isRequired) {
      if (prop.returnType.isMarkedNullable) {
        DefaultTestNull.testValue(value, annotation, prop)
      } else {
        // TODO: this can definitely be improved
        DefaultTestDefault.testValue(value, annotation, prop)
      }
    } else {
      DefaultTestDefault.testValue(value, annotation, prop)
    }
}