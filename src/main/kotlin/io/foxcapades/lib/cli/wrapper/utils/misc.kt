package io.foxcapades.lib.cli.wrapper.utils

internal inline fun <T, reified R : Any> T.takeAs(): R? = takeIf { it is R }?.let { it as R }