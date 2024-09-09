package io.foxcapades.lib.cli.builder.command

import io.foxcapades.lib.cli.builder.util.reflect.ValueAccessorReference
import kotlin.reflect.KCallable

interface ResolvedCommandRef<T : Any> : ResolvedCommand, ValueAccessorReference<T, Command, KCallable<Command>>
