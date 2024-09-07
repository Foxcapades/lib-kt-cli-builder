package io.foxcapades.lib.cli.builder.arg.impl

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.arg.filter.ArgumentPredicate
import io.foxcapades.lib.cli.builder.reflect.ValueAccessorReference
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.properties.BasicMutableDefaultableProperty
import io.foxcapades.lib.cli.builder.util.properties.Property
import io.foxcapades.lib.cli.builder.util.properties.getOrNull
import kotlin.reflect.KCallable

internal abstract class AbstractArgument<out A : Argument<V>, V>(
  default:     Property<V>,
  isRequired:  Boolean,
  shouldQuote: Boolean,
  filter:      ArgumentPredicate<A, V>
)
  : BasicMutableDefaultableProperty<V>(if (default.isSet) 2 else 0, default.getOrNull(), null)
  , Argument<V>
{
  private val filter = filter

  @Suppress("UNCHECKED_CAST")
  protected inline val self get() = this as A

  override val isRequired = isRequired

  override val shouldQuote = shouldQuote

  override fun shouldSerialize(
    config:    CliSerializationConfig,
    reference: ValueAccessorReference<*, V, out KCallable<V>>,
  ) = filter.shouldInclude(self, reference, config)
}
