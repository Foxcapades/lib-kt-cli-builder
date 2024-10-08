package io.foxcapades.lib.cli.builder.arg.impl

import io.foxcapades.kt.prop.delegation.Property
import io.foxcapades.kt.prop.delegation.getOr
import io.foxcapades.kt.prop.delegation.getOrCompute
import io.foxcapades.kt.prop.delegation.getOrNull
import io.foxcapades.lib.cli.builder.arg.ArgOptions
import io.foxcapades.lib.cli.builder.arg.DelegateArgument
import io.foxcapades.lib.cli.builder.arg.NullableArgOptions
import io.foxcapades.lib.cli.builder.arg.filter.ArgUnsetFilter
import io.foxcapades.lib.cli.builder.arg.filter.ArgumentPredicate
import io.foxcapades.lib.cli.builder.arg.filter.unsafeCast
import io.foxcapades.lib.cli.builder.arg.format.ArgumentFormatter
import io.foxcapades.lib.cli.builder.arg.format.NonNullGeneralStringifier
import io.foxcapades.lib.cli.builder.arg.format.NullableGeneralStringifier
import io.foxcapades.lib.cli.builder.serial.*
import io.foxcapades.lib.cli.builder.util.*
import io.foxcapades.lib.cli.builder.util.properties.*
import io.foxcapades.lib.cli.builder.util.reflect.property
import io.foxcapades.lib.cli.builder.util.reflect.shouldQuote
import io.foxcapades.lib.cli.builder.util.values.ValueSource
import java.math.BigDecimal
import kotlin.reflect.KClass

internal class UniversalDelegateArgument<V>(
  default:     Property<V>,
  shouldQuote: Boolean,
  isRequired:  Boolean,
  private val  formatter: ArgumentFormatter<V>,
  private val  filter: ArgumentPredicate<V>
)
  : DelegateArgument<V>
  , AbstractDelegate<V>(
    if (default.isSet) 2 else 0,
    default.getOrNull(),
    null
  )
{
  override val shouldQuote = shouldQuote

  override val isRequired = isRequired

  constructor(
    type:        KClass<*>,
    nullable:    Boolean,
    default:     Property<V>,
    shouldQuote: Property<Boolean>,
    isRequired:  Property<Boolean>,
    formatter:   Property<ArgumentFormatter<V>>,
    filter:      Property<ArgumentPredicate<V>>
  ) : this(
    default     = default,
    shouldQuote = shouldQuote.getOrCompute { type.shouldQuote() },
    isRequired  = isRequired.getOr(false),
    formatter   = formatter.getOrCompute {
      @Suppress("UNCHECKED_CAST")
      (if (nullable) NullableGeneralStringifier else NonNullGeneralStringifier) as ArgumentFormatter<V>
    },
    filter      = filter.getOr(ArgUnsetFilter.unsafeCast())
  )

  override fun shouldSerialize(config: CliSerializationConfig, source: ValueSource) =
    filter.shouldInclude(this, config, source)

  override fun writeToString(writer: CliArgumentWriter<*, V>) =
    formatter.formatValue(get(), writer, this)

  companion object {
    @JvmStatic
    inline fun <reified T : Any> of(opts: NullableArgOptions<T>) = UniversalDelegateArgument<T?>(
      type        = T::class,
      nullable    = true,
      default     = NullableArgOptions<BigDecimal>::default.property(opts),
      shouldQuote = NullableArgOptions<BigDecimal>::shouldQuote.property<Boolean>(opts).setIfAbsent(false),
      isRequired  = NullableArgOptions<BigDecimal>::required.property(opts),
      formatter   = NullableArgOptions<BigDecimal>::formatter.property(opts),
      filter      = NullableArgOptions<BigDecimal>::filter.property(opts)
    )

    @JvmStatic
    inline fun <reified T : Any> of(opts: ArgOptions<T>) = UniversalDelegateArgument<T>(
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
