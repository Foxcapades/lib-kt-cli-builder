package io.foxcapades.lib.cli.builder.command.ref

import io.foxcapades.lib.cli.builder.util.reflect.ValueAccessorReference
import kotlin.reflect.KCallable

interface ResolvedCommandValueRef<P : Any, C : Any> : ResolvedCommand<C>, ValueAccessorReference<P, C, KCallable<C>> {
  override val parentComponent: ResolvedCommand<P>

  override val qualifiedName: String
    get() = "command " + super.qualifiedName
}
