@file:Suppress("NOTHING_TO_INLINE")
package io.foxcapades.lib.cli.builder.arg.ref

@Suppress("UNCHECKED_CAST")
internal inline fun ResolvedArgument<*>.forceAny() =
  this as ResolvedArgument<Any?>
