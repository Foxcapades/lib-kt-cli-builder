package io.foxcapades.lib.cli.wrapper.serial

import io.foxcapades.lib.cli.wrapper.Shell
import io.foxcapades.lib.cli.wrapper.serial.properties.PropertyNameFormatter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.shell.SH

/**
 * Command line call building configuration.
 *
 * Allows configuring how generated cli calls are formatted when constructed.
 *
 * @since 1.0.0
 * @author Elizabeth Paige Harper [https://github.com/foxcapades]
 */
data class CliSerializationConfig(

  /**
   * Form of flag that should be preferred when possible.
   *
   * If flag does not have a form matching this preference, it's other form will
   * be used.
   */
  val preferredFlagForm: FlagForm,

  /**
   * Prefix to use for long flags in the constructed cli string.
   *
   * Commonly, long flags are prefixed with two dashes (`--flag`) and short
   * flags with a single dash (`-f`), however, many commands use a single dash
   * for both long and short flags.
   */
  val longFlagPrefix: String,

  /**
   * Separator string to use between long flags and their associated argument
   * values.
   *
   * ```kt
   * val sep = "="
   * "--flag${sep}value" -> "--flag=value"
   * ```
   */
  val longFlagValueSeparator: String,

  /**
   * Prefix to use for long flags in the constructed cli string.
   *
   * Commonly, short flags are prefixed with a single dash (`-f`).
   */
  val shortFlagPrefix: String,

  /**
   * Separator string to use between short flags and their associated argument
   * values.
   *
   * ```kt
   * val sep = " "
   * "-f${sep}value" -> "-f value"
   * ```
   */
  val shortFlagValueSeparator: String,

  /**
   * Controls whether flags should be included in the CLI string even if they
   * are set to their default value.
   */
  val includeDefaultedFlags: IncludeDefault,

  /**
   * Controls whether positional arguments should be included in the CLI string
   * even if they are set to their default value.
   */
  val includeDefaultedPositionalArgs: IncludeDefault,

  /**
   * Fallback serializer that will be used to handle otherwise unhandled
   * `null` values.
   *
   * **WARNING**: This serializer is only used when the default argument
   * [ArgumentFormatter] is not capable of handling `null` values.
   */
  val nullSerializer: NullSerializer,

  val propertyNameFormatter: PropertyNameFormatter,

  val fallbackSerializer: ArgumentFormatter<in Any>,

  val targetShell: Shell,
) {
  object Defaults {
    @JvmStatic
    val PreferredFlagForm get() = FlagForm.Long

    const val LongFlagPrefix = "--"

    const val LongFlagValueSeparator = "="

    const val ShortFlagPrefix = "-"

    const val ShortFlagValueSeparator = " "

    @JvmStatic
    val IncludeDefaultedFlags get() = IncludeDefault.Never

    @JvmStatic
    val IncludeDefaultedPositionalArgs get() = IncludeDefault.Always

    @JvmStatic
    val NullSerializer get() = io.foxcapades.lib.cli.wrapper.serial.NullSerializer.useEmptyString()

    @JvmStatic
    val PropertyNameFormatter get() = io.foxcapades.lib.cli.wrapper.serial.properties.PropertyNameFormatter.KebabCase

    @JvmStatic
    val FallbackSerializer get() = NonNullGeneralStringifier

    // TODO: determine default based on OS
    @JvmStatic
    val TargetShell: Shell get() = SH()
  }

  constructor() : this(
    preferredFlagForm              = Defaults.PreferredFlagForm,
    longFlagPrefix                 = Defaults.LongFlagPrefix,
    longFlagValueSeparator         = Defaults.LongFlagValueSeparator,
    shortFlagPrefix                = Defaults.ShortFlagPrefix,
    shortFlagValueSeparator        = Defaults.ShortFlagValueSeparator,
    includeDefaultedFlags          = Defaults.IncludeDefaultedFlags,
    includeDefaultedPositionalArgs = Defaults.IncludeDefaultedPositionalArgs,
    nullSerializer                 = Defaults.NullSerializer,
    propertyNameFormatter          = Defaults.PropertyNameFormatter,
    fallbackSerializer             = Defaults.FallbackSerializer,
    targetShell                    = Defaults.TargetShell,
  )

  constructor(builder: Builder) : this(
    preferredFlagForm              = builder.preferredFlagForm,
    longFlagPrefix                 = builder.longFlagPrefix,
    longFlagValueSeparator         = builder.longFlagValueSeparator,
    shortFlagPrefix                = builder.shortFlagPrefix,
    shortFlagValueSeparator        = builder.shortFlagValueSeparator,
    includeDefaultedFlags          = builder.includeDefaultedFlags,
    includeDefaultedPositionalArgs = builder.includeDefaultedPositionalArgs,
    nullSerializer                 = builder.nullSerializer,
    propertyNameFormatter          = builder.propertyNameFormatter,
    fallbackSerializer             = builder.fallbackSerializer,
    targetShell                    = builder.targetShell,
  )

  constructor(action: Builder.() -> Unit) : this(Builder().apply(action))

  data class Builder(
    var preferredFlagForm: FlagForm,
    var longFlagPrefix: String,
    var longFlagValueSeparator: String,
    var shortFlagPrefix: String,
    var shortFlagValueSeparator: String,
    var includeDefaultedFlags: IncludeDefault,
    var includeDefaultedPositionalArgs: IncludeDefault,
    var nullSerializer: NullSerializer,
    var propertyNameFormatter: PropertyNameFormatter,
    var fallbackSerializer: ArgumentFormatter<in Any>,
    var targetShell: Shell,
  ) {
    constructor() : this(
      preferredFlagForm              = Defaults.PreferredFlagForm,
      longFlagPrefix                 = Defaults.LongFlagPrefix,
      longFlagValueSeparator         = Defaults.LongFlagValueSeparator,
      shortFlagPrefix                = Defaults.ShortFlagPrefix,
      shortFlagValueSeparator        = Defaults.ShortFlagValueSeparator,
      includeDefaultedFlags          = Defaults.IncludeDefaultedFlags,
      includeDefaultedPositionalArgs = Defaults.IncludeDefaultedPositionalArgs,
      nullSerializer                 = Defaults.NullSerializer,
      propertyNameFormatter          = Defaults.PropertyNameFormatter,
      fallbackSerializer             = Defaults.FallbackSerializer,
      targetShell                    = Defaults.TargetShell,
    )

    constructor(action: Builder.() -> Unit) : this() {
      action()
    }

    fun preferredFlagForm(value: FlagForm) = apply { preferredFlagForm = value }

    fun longFlagPrefix(value: String) = apply { longFlagPrefix = value }

    fun longFlagValueSeparator(value: String) = apply { longFlagValueSeparator = value }

    fun shortFlagPrefix(value: String) = apply { shortFlagPrefix = value }

    fun shortFlagValueSeparator(value: String) = apply { shortFlagValueSeparator = value }

    fun includeDefaultedFlags(value: IncludeDefault) = apply { includeDefaultedFlags = value }

    fun includeDefaultedPositionalArgs(value: IncludeDefault) = apply { includeDefaultedPositionalArgs = value }

    fun nullSerializer(value: NullSerializer) = apply { nullSerializer = value }

    fun propertyNameFormatter(value: PropertyNameFormatter) = apply { propertyNameFormatter = value }

    fun fallbackSerializer(value: ArgumentFormatter<in Any>) = apply { fallbackSerializer = value }

    fun targetShell(value: Shell) = apply { targetShell = value }

    fun build() = CliSerializationConfig(this)
  }
}

