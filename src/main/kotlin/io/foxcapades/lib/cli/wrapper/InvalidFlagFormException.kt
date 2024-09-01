package io.foxcapades.lib.cli.wrapper

import io.foxcapades.lib.cli.wrapper.utils.isASCII

class InvalidFlagFormException(
  message: String,
  val invalidForm: InvalidForm
) : RuntimeException(message) {
  inline val isAboutShortFlag get() = invalidForm.isShort || invalidForm.isBoth

  inline val isAboutLongFlag get() = invalidForm.isLong || invalidForm.isBoth

  companion object {
    @JvmStatic
    fun invalidShortForm(property: String, type: String, form: Char) =
      InvalidFlagFormException(
        "$type::$property was annotated with an invalid short-form flag name: " +
          if (form.isASCII)
            "ASCII character with code ${form.code}"
          else
            "non-ASCII character with code dec(%1\$d), hex(%1\$04x)".format(form.code),
        InvalidForm.Short,
      )
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