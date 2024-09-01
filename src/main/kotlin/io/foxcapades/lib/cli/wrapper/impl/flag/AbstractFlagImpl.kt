@file:Suppress("NOTHING_TO_INLINE")

package io.foxcapades.lib.cli.wrapper.impl.flag

import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.Flag
import io.foxcapades.lib.cli.wrapper.serial.CliStringBuilder
import io.foxcapades.lib.cli.wrapper.utils.*
import kotlin.experimental.and
import kotlin.reflect.KProperty

internal sealed class AbstractFlagImpl<A: Argument<V>, V> private constructor(
  final override val longForm: String,
  final override val shortForm: Byte,
  final override val isRequired: Boolean,
  final override val argument: A,
  private val forms: Byte,
) : Flag<A, V> {
  final override val hasLongForm: Boolean
    get() = forms and 1 > 0

  final override val hasShortForm: Boolean
    get() = forms and 2 > 0

  constructor(longForm: String, shortForm: Byte, isRequired: Boolean, argument: A)
    : this(longForm, shortForm, isRequired, argument, 3)

  constructor(longForm: String, isRequired: Boolean, argument: A)
    : this(longForm, 0, isRequired, argument, 1)

  constructor(shortForm: Byte, isRequired: Boolean, argument: A)
    : this("", shortForm, isRequired, argument, 2)

  init {
    if (hasShortForm && !isCliSafeChar(shortForm))
      throw IllegalArgumentException("illegal short-form flag character used: raw value = $shortForm")

    if (hasLongForm) {
      if (longForm.isEmpty())
        throw IllegalArgumentException(maybeAppendShortFlag("attempted to set an empty string as a long flag value"))

      for (c in longForm) {
        if (!c.isASCII)
          throw IllegalArgumentException(maybeAppendShortFlag("attempted to use a non-ASCII character in a long-flag name"))
        if (!isCliSafeChar(c.code.toByte()))
          throw IllegalArgumentException(maybeAppendShortFlag("illegal character in long-form flag name"))
      }
    }
  }

  override fun get() = argument.get()

  override fun getValue(ref: Any?, property: KProperty<*>) = argument.getValue(ref, property)

  override fun set(value: V) = argument.set(value)

  override fun setValue(ref: Any?, property: KProperty<*>, value: V) = argument.setValue(ref, property, value)

  override fun unset() = argument.unset()

  override fun writeToString(builder: CliStringBuilder) {
    if (!hasLongForm || builder.config.preferredFlagForm.isShort && hasShortForm) {
      builder.appendShortFlag(shortForm, true)
    } else {
      builder.appendLongFlag(longForm, true)
    }

    argument.writeToString(builder)
  }

  private inline fun isCliSafeChar(b: Byte) =
    b.isLowerAlpha
      || b.isUpperAlpha
      || b.isDigit
      || b == ASCII_PERCENT
      || b == ASCII_PLUS
      || b == ASCII_COMMA
      || b == ASCII_PERIOD
      || b == ASCII_SOLIDUS
      || b == ASCII_COLON
      || b == ASCII_EQUALS
      || b == ASCII_COMMERCIAL_AT
      || b == ASCII_CARET
      || b == ASCII_UNDERSCORE

  private inline fun maybeAppendShortFlag(msg: String) =
    if (hasShortForm) "$msg on flag '${shortForm.toInt().toChar()}'" else msg
}
