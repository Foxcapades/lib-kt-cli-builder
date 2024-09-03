package io.foxcapades.lib.cli.wrapper

import io.foxcapades.lib.cli.wrapper.serial.CliSerializationConfig
import io.foxcapades.lib.cli.wrapper.util.safeName
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

class UnsetCliComponentException : RuntimeException, ResolvedComponent<Any, Any?> {
  private val ref: ResolvedComponent<Any, Any?>

  override val property: KProperty1<Any, Any?>
    get() = ref.property

  override val type: KClass<out Any>
    get() = ref.type

  constructor(flag: ResolvedFlag<*, *>, config: CliSerializationConfig) : super(flag.makeErrorMessage(config)) {
    @Suppress("UNCHECKED_CAST")
    ref = flag as ResolvedFlag<Any, Any?>
  }
}

private fun ResolvedFlag<*, *>.makeErrorMessage(config: CliSerializationConfig) =
  "Flag " + safeName(config) + " (" + type.safeName + "::" + property.name +
    ") is marked as required, but no value was set."
