package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.Flag
import io.foxcapades.lib.cli.wrapper.ResolvedFlag
import io.foxcapades.lib.cli.wrapper.serial.CliAppender
import io.foxcapades.lib.cli.wrapper.unsafeAnyType
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

internal class PinnedFlag<T : Any>(
  override val type: KClass<out T>,
  override val property: KProperty1<T, *>,
  internal val actualFlag: Flag<*, *>
) : ResolvedFlag<T, Any?> {
  override val hasLongForm
    get() = actualFlag.hasLongForm

  override val longForm
    get() = actualFlag.longForm

  override val hasShortForm
    get() = actualFlag.hasShortForm

  override val shortForm
    get() = actualFlag.shortForm

  @Suppress("UNCHECKED_CAST")
  override val argument
    get() = actualFlag.argument as Argument<Any?>

  override val isRequired
    get() = actualFlag.isRequired

  override fun writeToString(builder: CliAppender<*, Any?>) = actualFlag.unsafeAnyType().writeToString(builder)
}
