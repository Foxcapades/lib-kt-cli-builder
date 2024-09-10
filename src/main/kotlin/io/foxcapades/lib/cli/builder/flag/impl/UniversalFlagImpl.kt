package io.foxcapades.lib.cli.builder.flag.impl

import io.foxcapades.lib.cli.builder.*
import io.foxcapades.lib.cli.builder.arg.ArgOptions
import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.arg.NullableArgOptions
import io.foxcapades.lib.cli.builder.arg.impl.UniversalArgumentImpl
import io.foxcapades.lib.cli.builder.flag.Flag
import io.foxcapades.lib.cli.builder.flag.FlagOptions
import io.foxcapades.lib.cli.builder.flag.NullableFlagOptions
import io.foxcapades.lib.cli.builder.flag.filter.FlagPredicate
import io.foxcapades.lib.cli.builder.serial.*
import io.foxcapades.lib.cli.builder.util.properties.Property
import io.foxcapades.lib.cli.builder.util.properties.getOr
import io.foxcapades.lib.cli.builder.util.properties.getOrNull
import io.foxcapades.lib.cli.builder.util.reflect.ValueAccessorReference
import io.foxcapades.lib.cli.builder.util.reflect.property
import kotlin.reflect.KCallable

internal class UniversalFlagImpl<A : Argument<V>, V>(
  longForm:    Property<String>,
  shortForm:   Property<Char>,
  isRequired:  Property<Boolean>,
  filter:      Property<FlagPredicate<Flag<A, V>, A, V>>,
  argument:    A,
) : Flag<A, V> {
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
    reference: ValueAccessorReference<*, V, KCallable<V>>?,
  ) =
    fi?.shouldInclude(this, reference, config) ?: super.shouldSerialize(config, reference)

  companion object {
    fun <T : Any> of(config: FlagOptions<T>): Flag<Argument<T>, T> =
      UniversalFlagImpl(
        longForm   = FlagOptions<T>::longForm.property(config),
        shortForm  = FlagOptions<T>::shortForm.property(config),
        isRequired = FlagOptions<T>::required.property(config),
        filter     = FlagOptions<T>::flagFilter.property(config),
        argument   = UniversalArgumentImpl(
          config.type,
          false,
          default     = ArgOptions<T>::default.property(config.argument),
          shouldQuote = ArgOptions<T>::shouldQuote.property(config.argument),
          isRequired  = ArgOptions<T>::required.property(config.argument),
          filter      = ArgOptions<T>::filter.property(config.argument),
          formatter   = ArgOptions<T>::formatter.property(config.argument),
        ),
      )

    fun <T : Any> of(config: NullableFlagOptions<T>): Flag<Argument<T?>, T?> =
      UniversalFlagImpl(
        longForm   = NullableFlagOptions<T>::longForm.property(config),
        shortForm  = NullableFlagOptions<T>::shortForm.property(config),
        isRequired = NullableFlagOptions<T>::required.property(config),
        filter     = NullableFlagOptions<T>::flagFilter.property(config),
        argument   = UniversalArgumentImpl(
          config.type,
          false,
          default     = NullableArgOptions<T>::default.property(config.argument),
          shouldQuote = NullableArgOptions<T>::shouldQuote.property(config.argument),
          isRequired  = NullableArgOptions<T>::required.property(config.argument),
          filter      = NullableArgOptions<T>::filter.property(config.argument),
          formatter   = NullableArgOptions<T>::formatter.property(config.argument),
        ),
      )
  }
}

