package io.foxcapades.lib.cli.builder.util

import org.slf4j.LoggerFactory

internal inline fun <reified T : Any> T.logger() = LoggerFactory.getLogger(T::class.java)!!
