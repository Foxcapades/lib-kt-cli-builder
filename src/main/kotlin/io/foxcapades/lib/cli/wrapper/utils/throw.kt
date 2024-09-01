@file:Suppress("NOTHING_TO_INLINE")

package io.foxcapades.lib.cli.wrapper.utils

@Suppress("FunctionName")
internal inline fun BUG(): Nothing = throw IllegalStateException("this is a bug")