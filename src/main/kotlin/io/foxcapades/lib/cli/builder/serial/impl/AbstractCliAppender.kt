package io.foxcapades.lib.cli.builder.serial.impl

import io.foxcapades.lib.cli.builder.CliSerializationException
import io.foxcapades.lib.cli.builder.arg.ref.ResolvedArgument
import io.foxcapades.lib.cli.builder.flag.ref.ResolvedFlag
import io.foxcapades.lib.cli.builder.serial.CliArgumentWriter
import io.foxcapades.lib.cli.builder.serial.CliFlagWriter
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig

internal abstract class AbstractCliAppender<T : Any>(
  config: CliSerializationConfig
)
  : CliFlagWriter<T, Any?>
  , CliArgumentWriter<T, Any?>
{

  override val config = config

  protected abstract val flag: ResolvedFlag<Any?>

  protected abstract val argument: ResolvedArgument<Any?>

  override fun writePreferredForm(): CliArgumentWriter<T, Any?> {
    if (flag.hasLongForm) {
      return if (config.preferredFlagForm.isLong || !flag.hasShortForm)
        writeLongForm()
      else
        writeShortForm()
    }

    if (flag.hasShortForm)
      return writeShortForm()

    if (flag.valueSource.hasName) {
      val flagName = flag.valueSource.name.run { takeIf { it.startsWith("get") }?.substring(3) ?: this }

      if (flagName.isNotBlank() && flagName.all { config.targetShell.isFlagSafe(it) })
        return writeLongForm(config.propertyNameFormatter.format(flagName, config))
      else
        throw CliSerializationException("Flag instance has no known or derivable long or short CLI names, Flag source was \"${flag.valueSource.reference}\"")
    }

    // TODO: make this a concrete type
    throw CliSerializationException("Flag instance has no known or derivable long or short CLI names")
  }

  override fun writePreferredForm(action: CliArgumentWriter<T, Any?>.() -> Unit) =
    writePreferredForm().action()

  override fun writeLongForm(): CliArgumentWriter<T, Any?> =
    writeLongForm(flag.longForm)

  override fun writeLongForm(action: CliArgumentWriter<T, Any?>.() -> Unit) =
    writeLongForm().action()

  override fun writeLongForm(custom: String, action: CliArgumentWriter<T, Any?>.() -> Unit) =
    writeLongForm(custom).action()

  override fun writeShortForm(): CliArgumentWriter<T, Any?> =
    writeShortForm(flag.shortForm)

  override fun writeShortForm(action: CliArgumentWriter<T, Any?>.() -> Unit) =
    writeShortForm().action()

  override fun writeShortForm(custom: Char, action: CliArgumentWriter<T, Any?>.() -> Unit) =
    writeShortForm(custom).action()

  //

  override fun rawWriteChar(char: Byte) =
    rawWriteChar(char.toInt().toChar())

  //

  override fun writeChar(char: Byte) =
    writeChar(char.toInt().toChar())

  //

  protected fun getPreferredForm(flag: ResolvedFlag<*>): String {
    if (flag.hasLongForm) {
      return if (config.preferredFlagForm.isLong || !flag.hasShortForm)
        config.longFlagPrefix + flag.longForm
      else
        config.shortFlagPrefix + flag.shortForm
    }

    if (flag.hasShortForm)
      return config.shortFlagPrefix + flag.shortForm

    if (flag.valueSource.hasName) {
      val flagName = flag.valueSource.name.run { takeIf { it.startsWith("get") }?.substring(3) ?: this }

      if (flagName.isNotBlank() && flagName.all { config.targetShell.isFlagSafe(it) })
        return config.longFlagPrefix + config.propertyNameFormatter.format(flagName, config)
      else
        throw CliSerializationException("Flag instance has no known or derivable long or short CLI names, Flag source was \"${flag.valueSource.reference}\"")
    }

    // TODO: make this a concrete type
    throw CliSerializationException("Flag instance has no known or derivable long or short CLI names")
  }
}
