package io.foxcapades.lib.cli.builder.flag.impl

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.flag.Flag
import io.foxcapades.lib.cli.builder.flag.filter.FlagPredicate
import io.foxcapades.lib.cli.builder.flag.filter.FlagSetFilter
import io.foxcapades.lib.cli.builder.flag.filter.unsafeCast
import io.foxcapades.lib.cli.builder.serial.CliFlagWriter
import io.foxcapades.lib.cli.builder.serial.writeArgument
import io.foxcapades.lib.cli.builder.util.properties.Property
import io.foxcapades.lib.cli.builder.util.properties.getOr

internal abstract class AbstractFlagImpl<Self : Flag<A, V>, A : Argument<V>, V>(
  longForm: Property<String>,
  shortForm: Property<Char>,
  isRequired: Property<Boolean>,
  filter: Property<FlagPredicate<Self, A, V>>,
  argument:   A,
) : Flag<A, V> {
  private val lf = longForm
  private val sf = shortForm

  protected val filter = filter.getOr(FlagSetFilter.unsafeCast())

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

  override fun writeToString(builder: CliFlagWriter<*, V>) {
    // TODO: handle optional arguments
    builder.writePreferredForm().writeArgument(argument)
  }
}
