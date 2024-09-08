package io.foxcapades.lib.cli.builder.arg.impl

import io.foxcapades.lib.cli.builder.arg.ArgOptions
import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.arg.NullableArgOptions
import io.foxcapades.lib.cli.builder.arg.filter.ArgSetFilter
import io.foxcapades.lib.cli.builder.arg.filter.ArgumentPredicate
import io.foxcapades.lib.cli.builder.arg.filter.unsafeCast
import io.foxcapades.lib.cli.builder.arg.format.ArgumentFormatter
import io.foxcapades.lib.cli.builder.arg.format.NonNullGeneralStringifier
import io.foxcapades.lib.cli.builder.arg.format.NullableGeneralStringifier
import io.foxcapades.lib.cli.builder.reflect.ValueAccessorReference
import io.foxcapades.lib.cli.builder.reflect.property
import io.foxcapades.lib.cli.builder.reflect.shouldQuote
import io.foxcapades.lib.cli.builder.serial.*
import io.foxcapades.lib.cli.builder.util.*
import io.foxcapades.lib.cli.builder.util.properties.*
import java.math.BigDecimal
import kotlin.reflect.KCallable
import kotlin.reflect.KClass

internal class UniversalArgumentImpl<V>(
  default:     Property<V>,
  shouldQuote: Boolean,
  isRequired:  Boolean,
  private val  formatter: ArgumentFormatter<V>,
  private val  filter: ArgumentPredicate<Argument<V>, V>
) : BasicMutableDefaultableProperty<V>(
  if (default.isSet) 2 else 0,
  default.getOrNull(),
  null
), Argument<V> {
  override val shouldQuote = shouldQuote

  override val isRequired = isRequired

  constructor(
    type:        KClass<*>,
    nullable:    Boolean,
    default:     Property<V>,
    shouldQuote: Property<Boolean>,
    isRequired:  Property<Boolean>,
    formatter:   Property<ArgumentFormatter<V>>,
    filter:      Property<ArgumentPredicate<Argument<V>, V>>
  ) : this(
    default     = default,
    shouldQuote = shouldQuote.getOrCompute { type.shouldQuote() },
    isRequired  = isRequired.getOr(false),
    formatter   = formatter.getOrCompute {
      @Suppress("UNCHECKED_CAST")
      (if (nullable) NullableGeneralStringifier else NonNullGeneralStringifier) as ArgumentFormatter<V>
    },
    filter      = filter.getOr(ArgSetFilter.unsafeCast())
  )

  override fun shouldSerialize(
    config:    CliSerializationConfig,
    reference: ValueAccessorReference<*, V, out KCallable<V>>,
  ) = filter.shouldInclude(this, reference, config)

  override fun writeToString(writer: CliArgumentWriter<*, V>) =
    formatter.formatValue(get(), writer)

  companion object {
    @JvmStatic
    inline fun <reified T : Any> of(opts: NullableArgOptions<T>) = UniversalArgumentImpl<T?>(
      type        = T::class,
      nullable    = true,
      default     = NullableArgOptions<BigDecimal>::default.property(opts),
      shouldQuote = NullableArgOptions<BigDecimal>::shouldQuote.property<Boolean>(opts).setIfAbsent(false),
      isRequired  = NullableArgOptions<BigDecimal>::required.property(opts),
      formatter   = NullableArgOptions<BigDecimal>::formatter.property(opts),
      filter      = NullableArgOptions<BigDecimal>::filter.property(opts)
    )

    @JvmStatic
    inline fun <reified T : Any> of(opts: ArgOptions<T>) = UniversalArgumentImpl<T>(
      type        = T::class,
      nullable    = false,
      default     = NullableArgOptions<BigDecimal>::default.property(opts),
      shouldQuote = NullableArgOptions<BigDecimal>::shouldQuote.property<Boolean>(opts).setIfAbsent(false),
      isRequired  = NullableArgOptions<BigDecimal>::required.property(opts),
      formatter   = NullableArgOptions<BigDecimal>::formatter.property(opts),
      filter      = NullableArgOptions<BigDecimal>::filter.property(opts)
    )
  }
}
