@file:Suppress("NOTHING_TO_INLINE")

package io.foxcapades.lib.cli.builder.flag

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.flag.impl.BooleanFlagImpl
import io.foxcapades.lib.cli.builder.flag.impl.UniversalFlagImpl

inline fun boolFlag(longForm: String, noinline action: BooleanFlagOptions.() -> Unit = {}) =
  booleanFlag(longForm, action)

inline fun boolFlag(shortForm: Char, noinline action: BooleanFlagOptions.() -> Unit = {}) =
  booleanFlag(shortForm, action)

inline fun boolFlag(noinline action: BooleanFlagOptions.() -> Unit = {}) =
  booleanFlag(action)

inline fun toggleFlag(longForm: String, noinline action: BooleanFlagOptions.() -> Unit = {}) =
  booleanFlag(longForm) {
    isToggleFlag = true
    action()
  }

inline fun toggleFlag(shortForm: Char, noinline action: BooleanFlagOptions.() -> Unit = {}) =
  booleanFlag(shortForm) {
    isToggleFlag = true
    action()
  }

inline fun toggleFlag(noinline action: BooleanFlagOptions.() -> Unit = {}) =
  booleanFlag {
    isToggleFlag = true
    action()
  }

inline fun booleanFlag(longForm: String, noinline action: BooleanFlagOptions.() -> Unit = {}) =
  booleanFlag { this.longForm = longForm; action() }

inline fun booleanFlag(shortForm: Char, noinline action: BooleanFlagOptions.() -> Unit = {}) =
  booleanFlag { this.shortForm = shortForm; action() }

fun booleanFlag(action: BooleanFlagOptions.() -> Unit = {}): BooleanFlag =
  BooleanFlagImpl(BooleanFlagOptions().also(action))

fun nullableBooleanFlag(action: NullableFlagOptions<Boolean>.() -> Unit = {}): Flag<Argument<Boolean?>, Boolean?> =
  UniversalFlagImpl.of(NullableFlagOptions(Boolean::class).also(action))

