package io.foxcapades.lib.cli.wrapper.serial.values

import io.foxcapades.lib.cli.wrapper.CliCallComponent
import io.foxcapades.lib.cli.wrapper.ResolvedArgument
import io.foxcapades.lib.cli.wrapper.ResolvedFlag
import io.foxcapades.lib.cli.wrapper.reflect.PropertyReference
import io.foxcapades.lib.cli.wrapper.serial.CliSerializationConfig

internal sealed class NullDefaultTest<P>
  : ComponentDefaultTest<Any?, P>
  where
    P : CliCallComponent
  , P : PropertyReference<*, Any?>
{
  override fun valueIsDefault(value: Any?, reference: P, config: CliSerializationConfig) = value == null
}

internal data object ArgNullDefaultTest : NullDefaultTest<ResolvedArgument<*, Any?>>(), ArgumentDefaultTest<Any?>

internal data object FlagNullDefaultTest : NullDefaultTest<ResolvedFlag<*, Any?>>(), FlagDefaultTest<Any?>
