package io.foxcapades.lib.cli.builder.arg.ref

import io.foxcapades.lib.cli.builder.util.reflect.ValueAccessorReference
import kotlin.reflect.KCallable

interface ResolvedDelegatedArgumentRef<T : Any, V> : ResolvedArgument<V>, ValueAccessorReference<T, V, KCallable<V>>
