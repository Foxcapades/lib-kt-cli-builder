package io.foxcapades.lib.cli.builder.arg.format

/**
 * Standard boolean value formatter definitions.
 *
 * @since 1.0.0
 */
@Suppress("unused")
object BooleanFormat {

  /**
   * Formats boolean values as either `"YES"` or `"NO"`.
   */
  @JvmStatic
  inline val YesNoUpper
    get() = ArgumentFormatter { it, c, _ -> c.writeString(if (it) "YES" else "NO") }

  /**
   * Formats boolean values as either `"Yes"` or `"No"`.
   */
  @JvmStatic
  inline val YesNoTitle
    get() = ArgumentFormatter { it, c, _ -> c.writeString(if (it) "Yes" else "No") }

  /**
   * Formats boolean values as either `"yes"` or `"no"`.
   */
  @JvmStatic
  inline val YesNoLower
    get() = ArgumentFormatter { it, c, _ -> c.writeString(if (it) "yes" else "no") }

  /**
   * Formats boolean values as either `"1"` or `"0"`.
   */
  @JvmStatic
  inline val Binary
    get() = ArgumentFormatter { it, c, _ -> c.writeChar(if (it) '1' else '0') }

  /**
   * Formats boolean values as either `"TRUE"` or `"FALSE"`.
   */
  @JvmStatic
  inline val TrueFalseUpper
    get() = ArgumentFormatter { it, c, _ -> c.writeString(if (it) "TRUE" else "FALSE") }

  /**
   * Formats boolean values as either `"True"` or `"False"`.
   */
  @JvmStatic
  inline val TrueFalseTitle
    get() = ArgumentFormatter { it, c, _ -> c.writeString(if (it) "True" else "False") }

  /**
   * Formats boolean values as either `"true"` or `"false"`.
   */
  @JvmStatic
  inline val TrueFalseLower
    get() = ArgumentFormatter { it, c, _ -> c.writeString(if (it) "true" else "false") }
}
