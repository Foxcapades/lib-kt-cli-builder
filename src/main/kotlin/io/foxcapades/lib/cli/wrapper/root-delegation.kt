package io.foxcapades.lib.cli.wrapper

import io.foxcapades.lib.cli.wrapper.flag.GeneralFlagImpl
import kotlin.reflect.KClass

inline fun <reified T : Any> flag(noinline action: FlagOptions<T>.() -> Unit): Flag<Argument<T>, T> =
  flag(T::class, action)

inline fun <reified T : Any> nullableFlag(noinline action: NullableFlagOptions<T>.() -> Unit): Flag<Argument<T?>, T?> =
  nullableFlag(T::class, action)

fun <T : Any> flag(type: KClass<out T>, action: FlagOptions<T>.() -> Unit): Flag<Argument<T>, T> =
  GeneralFlagImpl.of(FlagOptions(type).also(action))

fun <T : Any> nullableFlag(type: KClass<out T>, action: NullableFlagOptions<T>.() -> Unit): Flag<Argument<T?>, T?> =
  GeneralFlagImpl.of(NullableFlagOptions(type).also(action))
