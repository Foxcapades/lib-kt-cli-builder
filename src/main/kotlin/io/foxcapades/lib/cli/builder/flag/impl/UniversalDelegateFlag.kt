package io.foxcapades.lib.cli.builder.flag.impl

import io.foxcapades.kt.prop.delegation.Property
import io.foxcapades.kt.prop.delegation.getOr
import io.foxcapades.kt.prop.delegation.getOrNull
import io.foxcapades.lib.cli.builder.*
import io.foxcapades.lib.cli.builder.arg.ArgOptions
import io.foxcapades.lib.cli.builder.arg.DelegateArgument
import io.foxcapades.lib.cli.builder.arg.impl.UniversalDelegateArgument
import io.foxcapades.lib.cli.builder.flag.DelegateFlag
import io.foxcapades.lib.cli.builder.flag.FlagOptions
import io.foxcapades.lib.cli.builder.flag.filter.FlagPredicate
import io.foxcapades.lib.cli.builder.serial.*
import io.foxcapades.lib.cli.builder.util.reflect.property
import io.foxcapades.lib.cli.builder.util.values.ValueSource

internal class UniversalDelegateFlag<V>(
  longForm:    Property<String>,
  shortForm:   Property<Char>,
  isRequired:  Property<Boolean>,
  filter:      Property<FlagPredicate<V>>,
  argument:    DelegateArgument<V>,
) : DelegateFlag<V> {
  private val lf = longForm
  private val sf = shortForm
  private val fi = filter.getOrNull()

  override val hasLongForm
    get() = lf.isSet

  override val longForm
    get() = lf.get()

  override val hasShortForm
    get() = sf.isSet

  override val shortForm
    get() = sf.get()

  override val argument = argument

  override val isRequired = isRequired.getOr(false)

  override fun writeToString(writer: CliFlagWriter<*, V>) {
    // TODO: this should be able to handle optional argument values?
    writer.writePreferredForm().writeArgument(argument)
  }

  override fun shouldSerialize(
    config:    CliSerializationConfig,
    source: ValueSource,
  ) =
    fi?.shouldInclude(this, config, source) ?: argument.shouldSerialize(config, source)

  companion object {
    fun <T : Any> of(config: FlagOptions<T>) =
      UniversalDelegateFlag<T>(
        longForm   = FlagOptions<T>::longForm.property(config),
        shortForm  = FlagOptions<T>::shortForm.property(config),
        isRequired = FlagOptions<T>::required.property(config),
        filter     = FlagOptions<T>::flagFilter.property(config),
        argument   = UniversalDelegateArgument(
          config.type,
          false,
          default     = ArgOptions<T>::default.property(config.argument),
          shouldQuote = ArgOptions<T>::shouldQuote.property(config.argument),
          isRequired  = ArgOptions<T>::required.property(config.argument),
          filter      = ArgOptions<T>::filter.property(config.argument),
          formatter   = ArgOptions<T>::formatter.property(config.argument),
        ),
      )
  }
}

