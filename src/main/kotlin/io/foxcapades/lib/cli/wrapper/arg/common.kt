package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.ResolvedComponent
import io.foxcapades.lib.cli.wrapper.serial.CliSerializationConfig
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentPredicate
import io.foxcapades.lib.cli.wrapper.util.BasicMutableDefaultableProperty
import io.foxcapades.lib.cli.wrapper.util.Property
import io.foxcapades.lib.cli.wrapper.util.getOrNull

internal sealed class AbstractArgument<out A : Argument<V>, V>(
  default:     Property<V>,
  isRequired:  Boolean,
  shouldQuote: Boolean,
  filter:      ArgumentPredicate<A, V>
) : BasicMutableDefaultableProperty<V>(
  if (default.isSet) 2 else 0,
  default.getOrNull(),
  null,
), Argument<V> {
  private val filter = filter

  @Suppress("UNCHECKED_CAST")
  protected inline val self get() = this as A

  override val isRequired = isRequired

  override val shouldQuote = shouldQuote

  override fun shouldSerialize(config: CliSerializationConfig, reference: ResolvedComponent<*, V>) =
    filter.shouldInclude(self, reference, config)
}
