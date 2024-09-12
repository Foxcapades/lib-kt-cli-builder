@file:Suppress("NOTHING_TO_INLINE")
package io.foxcapades.lib.cli.builder.command.ref

@Suppress("UNCHECKED_CAST")
internal inline fun ResolvedCommand<*>.forceAny() = this as ResolvedCommand<Any>
