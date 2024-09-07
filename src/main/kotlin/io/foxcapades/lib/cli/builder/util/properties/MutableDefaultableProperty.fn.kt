@file:Suppress("NOTHING_TO_INLINE")
package io.foxcapades.lib.cli.builder.util.properties

fun <T> MutableDefaultableProperty(): MutableDefaultableProperty<T> =
  BasicMutableDefaultableProperty(0, null, null)

fun <T> MutableNonDefaultedProperty(value: T): MutableDefaultableProperty<T> =
  BasicMutableDefaultableProperty(1, null, value)

fun <T> MutableDefaultedProperty(default: T): MutableDefaultableProperty<T> =
  BasicMutableDefaultableProperty(2, default, null)

fun <T> MutableDefaultableProperty(default: T, value: T): MutableDefaultableProperty<T> =
  BasicMutableDefaultableProperty(3, default, value)
