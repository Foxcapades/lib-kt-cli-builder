package io.foxcapades.lib.cli.builder.arg.impl

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.arg.ResolvedArgumentOld
import kotlin.reflect.KCallable
import kotlin.reflect.KClass

/**
 * Reference to an argument delegate property pinned down to a specific
 * instance.
 */
internal class PinnedArgument<T : Any, V>(
  override val containingType:     KClass<out T>,
  override val instance: T,
  override val accessor: KCallable<V>,
  internal val delegate: Argument<V>,
) : ResolvedArgumentOld<T, V>, Argument<V> by delegate
