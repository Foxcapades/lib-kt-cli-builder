package io.foxcapades.lib.cli.wrapper.utils

import kotlin.reflect.KClass

internal inline fun <T, reified R: Any> Iterable<T>.findInstance(): R? = find { it is R }?.let { it as R }