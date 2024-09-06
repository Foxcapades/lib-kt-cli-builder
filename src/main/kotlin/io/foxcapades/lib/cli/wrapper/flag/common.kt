package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.Flag
import io.foxcapades.lib.cli.wrapper.putPreferredFlagForm
import io.foxcapades.lib.cli.wrapper.serial.CliAppender
import io.foxcapades.lib.cli.wrapper.serial.values.FlagPredicate
import io.foxcapades.lib.cli.wrapper.serial.values.FlagSetFilter
import io.foxcapades.lib.cli.wrapper.serial.values.unsafeCast
import io.foxcapades.lib.cli.wrapper.util.Property
import io.foxcapades.lib.cli.wrapper.util.getOr

internal sealed class AbstractFlagImpl<Self : Flag<A, V>, A: Argument<V>, V>(
  longForm:   Property<String>,
  shortForm:  Property<Char>,
  isRequired: Property<Boolean>,
  filter:     Property<FlagPredicate<Self, A, V>>,
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

  override fun writeToString(builder: CliAppender<*, V>) {
    // TODO: handle optional arguments
    builder.putPreferredFlagForm(this, true).putArgument(argument)
  }
}
