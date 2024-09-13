package io.foxcapades.lib.cli.builder.flag.impl

import io.foxcapades.kt.prop.delegation.Property
import io.foxcapades.kt.prop.delegation.getOr
import io.foxcapades.lib.cli.builder.arg.DelegateArgument
import io.foxcapades.lib.cli.builder.arg.impl.BasicDelegateArgument
import io.foxcapades.lib.cli.builder.flag.DelegateFlag
import io.foxcapades.lib.cli.builder.flag.FlagOptions
import io.foxcapades.lib.cli.builder.flag.filter.FlagPredicate
import io.foxcapades.lib.cli.builder.flag.filter.FlagSetFilter
import io.foxcapades.lib.cli.builder.flag.filter.unsafeCast
import io.foxcapades.lib.cli.builder.serial.CliFlagWriter
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.serial.writeArgument
import io.foxcapades.lib.cli.builder.util.reflect.property
import io.foxcapades.lib.cli.builder.util.values.ValueSource

internal open class BasicDelegateFlag<V>(
  longForm:   Property<String>,
  shortForm:  Property<Char>,
  isRequired: Property<Boolean>,
  filter:     Property<FlagPredicate<V>>,
  argument:   DelegateArgument<V>,
) : DelegateFlag<V> {
  private val lf = longForm
  private val sf = shortForm

  private val filter = filter.getOr(FlagSetFilter.unsafeCast())

  override val hasLongForm
    get() = lf.isSet

  override val longForm
    get() = lf.get()

  override val hasShortForm
    get() = sf.isSet

  override val shortForm
    get() = sf.get()

  override val isRequired = isRequired.getOr(false)

  override val argument = argument

  override fun shouldSerialize(config: CliSerializationConfig, source: ValueSource) =
    filter.shouldInclude(this, config, source)

  override fun writeToString(writer: CliFlagWriter<*, V>) {
    // TODO: handle optional arguments
    writer.writePreferredForm().writeArgument(argument)
  }

  companion object {
    internal fun <V : Any> of(opts: FlagOptions<V>) = BasicDelegateFlag<V>(
      longForm   = FlagOptions<V>::longForm.property(opts),
      shortForm  = FlagOptions<V>::shortForm.property(opts),
      isRequired = FlagOptions<V>::required.property(opts),
      filter     = FlagOptions<V>::flagFilter.property(opts),
      argument   = BasicDelegateArgument.of(opts.argument)
    )
  }
}
