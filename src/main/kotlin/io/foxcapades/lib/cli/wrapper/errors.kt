package io.foxcapades.lib.cli.wrapper

import io.foxcapades.lib.cli.wrapper.serial.CliSerializationConfig
import io.foxcapades.lib.cli.wrapper.util.NoSuchDefaultValueException
import io.foxcapades.lib.cli.wrapper.util.isPrintable
import io.foxcapades.lib.cli.wrapper.util.safeName

class InvalidFlagFormException(
  message: String,
  val invalidForm: InvalidForm
) : RuntimeException(message) {
  inline val isAboutShortFlag get() = invalidForm.isShort || invalidForm.isBoth

  inline val isAboutLongFlag get() = invalidForm.isLong || invalidForm.isBoth

  companion object {
    fun invalidBothForms(flag: ResolvedFlag<*, *>): InvalidFlagFormException {
      val msg = StringBuilder()
        .append(flag.type.safeName)
        .append("::")
        .append(flag.accessor.name)
        .append(" was configured with both invalid short-form and invalid long-form names: (short=")

      if (flag.shortForm.isPrintable) {
        msg.append(flag.shortForm)
      } else {
        msg.append("\\u%04x".format(flag.shortForm.code))
      }

      msg.append(", long='")

      for (c in flag.longForm) {
        if (c.isPrintable)
          msg.append(c)
        else
          msg.append("\\u%04x".format(c.code))
      }

      msg.append("')")

      return InvalidFlagFormException(msg.toString(), InvalidForm.Both)
    }

    @JvmStatic
    fun invalidShortForm(flag: ResolvedFlag<*, *>): InvalidFlagFormException {
      val msg = startSharedMessage(flag, false)

      if (flag.shortForm.isPrintable) {
        msg.append(flag.shortForm)
      } else {
        msg.append("\\u%04x".format(flag.shortForm.code))
      }

      return InvalidFlagFormException(msg.toString(), InvalidForm.Short)
    }

    @JvmStatic
    fun invalidLongForm(flag: ResolvedFlag<*, *>): InvalidFlagFormException {
      val msg = startSharedMessage(flag, true).append('\'')

      for (c in flag.longForm) {
        if (c.isPrintable)
          msg.append(c)
        else
          msg.append("\\u%04x".format(c.code))
      }

      msg.append('\'')

      return InvalidFlagFormException(msg.toString(), InvalidForm.Long)
    }

    @Suppress("NOTHING_TO_INLINE")
    private inline fun startSharedMessage(flag: ResolvedFlag<*, *>, long: Boolean) =
      StringBuilder()
        .append(flag.type.safeName)
        .append("::")
        .append(flag.accessor.name)
        .append(" was configured with an invalid ")
        .append(if (long) "long" else "short")
        .append("form flag name: ")
  }

  enum class InvalidForm {
    Short,
    Long,
    Both;

    inline val isShort get() = this == Short
    inline val isLong get() = this == Long
    inline val isBoth get() = this == Both
  }
}

open class UnsetArgumentDefaultException(msg: String) : NoSuchDefaultValueException(msg) {
  constructor() : this("attempted to fetch the default value for an argument that had none")
}

private fun ResolvedFlag<*, *>.makeErrorMessage(config: CliSerializationConfig) =
  "Flag " + safeName(config) + " (" + type.safeName + "::" + name +
    ") is marked as required, but no value was set."
