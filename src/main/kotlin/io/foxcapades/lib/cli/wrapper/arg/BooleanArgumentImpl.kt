package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.util.Property
import io.foxcapades.lib.cli.wrapper.util.getOr

internal class BooleanArgumentImpl : AbstractScalarArgument<Boolean>, BooleanArgument {
  constructor(
    default: Property<Boolean>,
    shouldQuote: Property<Boolean>,
    formatter: Property<ArgumentFormatter<Boolean>>
  ) : super(
    default     = default.getOr(false),
    hasDefault  = default.isSet,
    shouldQuote = shouldQuote.getOr(false),
    formatter.getOr(ArgumentFormatter(Boolean::toString))
  )

  constructor(default: Boolean, formatter: ArgumentFormatter<Boolean>)
    : super(default, true, false, formatter)

  constructor(default: Boolean)
    : super(default, true, false, ArgumentFormatter(Boolean::toString))

  constructor(formatter: ArgumentFormatter<Boolean>)
    : super(false, false, false, formatter)

  constructor()
    : super(false, false, false, ArgumentFormatter(Boolean::toString))
}
