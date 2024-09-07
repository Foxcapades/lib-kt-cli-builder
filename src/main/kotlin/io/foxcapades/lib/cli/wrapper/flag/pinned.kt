package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.Flag
import io.foxcapades.lib.cli.wrapper.ResolvedFlag
import io.foxcapades.lib.cli.wrapper.serial.CliFlagWriter
import io.foxcapades.lib.cli.wrapper.unsafeAnyType
import kotlin.reflect.KCallable
import kotlin.reflect.KClass

internal class PinnedFlag<T : Any>(
  override val type:       KClass<out T>,
  override val instance:   T,
  override val accessor:   KCallable<Any?>,
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

  override fun writeToString(builder: CliFlagWriter<*, Any?>) =
    actualFlag.unsafeAnyType().writeToString(builder)
}
