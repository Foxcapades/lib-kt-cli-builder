package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.*
import io.foxcapades.lib.cli.wrapper.arg.GeneralArgumentImpl
import io.foxcapades.lib.cli.wrapper.reflect.ValueAccessorReference
import io.foxcapades.lib.cli.wrapper.serial.*
import io.foxcapades.lib.cli.wrapper.serial.values.FlagPredicate
import io.foxcapades.lib.cli.wrapper.util.Property
import io.foxcapades.lib.cli.wrapper.util.getOr
import io.foxcapades.lib.cli.wrapper.util.getOrNull
import io.foxcapades.lib.cli.wrapper.util.property
import kotlin.reflect.KCallable

internal class GeneralFlagImpl<A : Argument<V>, V>(
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

  override fun writeToString(builder: CliFlagWriter<*, V>) {
    // TODO: this should be able to handle optional argument values?
    builder.writePreferredForm().writeArgument(argument)
  }

  override fun shouldSerialize(
    config:    CliSerializationConfig,
    reference: ValueAccessorReference<*, V, out KCallable<V>>,
  ) =
    fi?.shouldInclude(this, reference, config) ?: super.shouldSerialize(config, reference)

  companion object {
    fun <T : Any> of(config: FlagOptions<T>): Flag<Argument<T>, T> =
      GeneralFlagImpl(
        longForm   = FlagOptions<T>::longForm.property(config),
        shortForm  = FlagOptions<T>::shortForm.property(config),
        isRequired = FlagOptions<T>::required.property(config),
        filter     = FlagOptions<T>::flagFilter.property(config),
        argument   = GeneralArgumentImpl(
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
      GeneralFlagImpl(
        longForm   = NullableFlagOptions<T>::longForm.property(config),
        shortForm  = NullableFlagOptions<T>::shortForm.property(config),
        isRequired = NullableFlagOptions<T>::required.property(config),
        filter     = NullableFlagOptions<T>::flagFilter.property(config),
        argument   = GeneralArgumentImpl(
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

