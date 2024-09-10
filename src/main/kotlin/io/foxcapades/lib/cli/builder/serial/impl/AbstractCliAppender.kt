package io.foxcapades.lib.cli.builder.serial.impl

import io.foxcapades.lib.cli.builder.arg.ref.ResolvedArgument
import io.foxcapades.lib.cli.builder.flag.ref.ResolvedFlag
import io.foxcapades.lib.cli.builder.serial.CliArgumentWriter
import io.foxcapades.lib.cli.builder.serial.CliFlagWriter
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.reflect.ValueAccessorReference

internal abstract class AbstractCliAppender<T : Any>(
  config: CliSerializationConfig
) : CliFlagWriter<T, Any?>, CliArgumentWriter<T, Any?> {

  override val config = config

  protected abstract val flag: ResolvedFlag<Any?>

  protected abstract val argument: ResolvedArgument<Any?>

  override fun writePreferredForm(): CliArgumentWriter<T, Any?> =
    if (flag.hasLongForm) {
      if (config.preferredFlagForm.isLong || !flag.hasShortForm)
        writeLongForm()
      else
        writeShortForm()
    } else if (flag.hasShortForm) {
      writeShortForm()
    } else if (flag is ValueAccessorReference<*, *, *>) {
      val f = flag as ValueAccessorReference<*, *, *>
      writeLongForm(config.propertyNameFormatter.format(f.name.takeUnless { it.startsWith("get") } ?: f.name.substring(3), config))
    } else {
      // TODO: make this a concrete type
      throw IllegalStateException("Flag instance has no known or derivable long or short CLI names")
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
}
