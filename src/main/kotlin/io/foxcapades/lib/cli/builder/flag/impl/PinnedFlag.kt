package io.foxcapades.lib.cli.builder.flag.impl

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.flag.Flag
import io.foxcapades.lib.cli.builder.flag.ResolvedFlagOld
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

internal class PinnedFlag<T : Any, V>(
  override val containingType:     KClass<out T>,
  override val instance: T,
  override val accessor: KProperty1<T, V>,
  internal val delegate: Flag<Argument<V>, V>
) : ResolvedFlagOld<T, V>, Flag<Argument<V>, V> by delegate
