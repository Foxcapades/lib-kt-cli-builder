package io.foxcapades.lib.cli.wrapper.serial.values

import io.foxcapades.lib.cli.wrapper.ResolvedArgument
import io.foxcapades.lib.cli.wrapper.ResolvedFlag
import io.foxcapades.lib.cli.wrapper.serial.CliSerializationConfig

internal object ArgDefaultTestReqDependent : ArgumentDefaultTest<Any?> {
  override fun valueIsDefault(value: Any?, reference: ResolvedArgument<*, Any?>, config: CliSerializationConfig) =
    (if (reference.propertyIsNullable) ArgNullDefaultTest else ArgGeneralDefaultTest).valueIsDefault(value, reference, config)
}

internal object FlagDefaultTestReqDependent : FlagDefaultTest<Any?> {
  override fun valueIsDefault(value: Any?, reference: ResolvedFlag<*, Any?>, config: CliSerializationConfig) =
    (if (reference.propertyIsNullable) FlagNullDefaultTest else FlagGeneralDefaultTest).valueIsDefault(value, reference, config)
}

