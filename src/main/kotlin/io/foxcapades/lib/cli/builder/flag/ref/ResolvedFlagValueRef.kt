package io.foxcapades.lib.cli.builder.flag.ref

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.flag.Flag
import io.foxcapades.lib.cli.builder.util.reflect.ValueAccessorReference
import kotlin.reflect.KCallable

interface ResolvedFlagValueRef<T : Any, V>
  : ResolvedFlag<V>
  , ValueAccessorReference<T, Flag<Argument<V>, V>, KCallable<Flag<Argument<V>, V>>>
{
  override val qualifiedName: String
    get() = super.qualifiedName
}
