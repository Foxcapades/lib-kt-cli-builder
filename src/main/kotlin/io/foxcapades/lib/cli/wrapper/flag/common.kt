package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.Flag
import io.foxcapades.lib.cli.wrapper.serial.CliAppender
import io.foxcapades.lib.cli.wrapper.util.CHAR_NULL
import io.foxcapades.lib.cli.wrapper.util.Property
import io.foxcapades.lib.cli.wrapper.util.getOr

internal sealed class AbstractFlagImpl<A: Argument<V>, V> private constructor(
  override val hasLongForm: Boolean,
  override val longForm: String,
  override val hasShortForm: Boolean,
  override val shortForm: Char,
  override val isRequired: Boolean,
  override val argument: A,
) : Flag<A, V> {
  constructor(lf: Property<String>, sf: Property<Char>, isRequired: Property<Boolean>, argument: A)
    : this(lf.isSet, lf.getOr(""), sf.isSet, sf.getOr(CHAR_NULL), isRequired.getOr(false), argument)

  constructor(longForm: String, shortForm: Char, isRequired: Boolean, argument: A)
    : this(true, longForm, true, shortForm, isRequired, argument)

  constructor(longForm: String, isRequired: Boolean, argument: A)
    : this(true, longForm, false, CHAR_NULL, isRequired, argument)

  constructor(shortForm: Char, isRequired: Boolean, argument: A)
    : this(false, "", true, shortForm, isRequired, argument)

  override fun writeToString(builder: CliAppender) {
    if (!hasLongForm || builder.config.preferredFlagForm.isShort && hasShortForm) {
      builder.putShortFlag(shortForm, true)
    } else {
      builder.putLongFlag(longForm, true)
    }

    builder.putArgument(argument)
  }
}
