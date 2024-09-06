package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.ResolvedComponent
import io.foxcapades.lib.cli.wrapper.reflect.shouldQuote
import io.foxcapades.lib.cli.wrapper.serial.CliArgumentAppender
import io.foxcapades.lib.cli.wrapper.serial.CliSerializationConfig
import io.foxcapades.lib.cli.wrapper.serial.NonNullGeneralStringifier
import io.foxcapades.lib.cli.wrapper.serial.NullableGeneralStringifier
import io.foxcapades.lib.cli.wrapper.serial.values.ArgSetFilter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentPredicate
import io.foxcapades.lib.cli.wrapper.serial.values.unsafeCast
import io.foxcapades.lib.cli.wrapper.util.*
import kotlin.reflect.KClass

internal class GeneralArgumentImpl<V>(
  default: Property<V>,
  shouldQuote: Boolean,
  isRequired: Boolean,
  private val formatter: ArgumentFormatter<V>,
  private val filter: ArgumentPredicate<Argument<V>, V>
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
    filter      = (filter.getOrNull() ?: ArgSetFilter).unsafeCast()
  )

  override fun shouldSerialize(config: CliSerializationConfig, reference: ResolvedComponent<*, V>) =
    filter.shouldInclude(this, reference, config)

  override fun writeToString(builder: CliArgumentAppender) =
    formatter.formatValue(get(), builder)
}
