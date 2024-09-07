package io.foxcapades.lib.cli.builder.arg.format

object BooleanFormat {
  @JvmStatic
  inline val YesNoUpper
    get() = ArgumentFormatter { it, c -> c.writeString(if (it) "YES" else "NO") }

  @JvmStatic
  inline val YesNoTitle
    get() = ArgumentFormatter { it, c -> c.writeString(if (it) "Yes" else "No") }

  @JvmStatic
  inline val YesNoLower
    get() = ArgumentFormatter { it, c -> c.writeString(if (it) "yes" else "no") }

  @JvmStatic
  inline val Binary
    get() = ArgumentFormatter { it, c -> c.writeChar(if (it) '1' else '0') }

  @JvmStatic
  inline val TrueFalseUpper
    get() = ArgumentFormatter { it, c -> c.writeString(if (it) "TRUE" else "FALSE") }

  @JvmStatic
  inline val TrueFalseTitle
    get() = ArgumentFormatter { it, c -> c.writeString(if (it) "True" else "False") }

  @JvmStatic
  inline val TrueFalseLower
    get() = ArgumentFormatter { it, c -> c.writeString(if (it) "true" else "false") }
}
