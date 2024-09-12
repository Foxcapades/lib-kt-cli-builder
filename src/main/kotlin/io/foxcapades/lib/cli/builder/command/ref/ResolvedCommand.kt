package io.foxcapades.lib.cli.builder.command.ref

import io.foxcapades.lib.cli.builder.command.Command
import io.foxcapades.lib.cli.builder.component.ResolvedComponent
import kotlin.reflect.KClass

sealed interface ResolvedCommand<C : Any> : ResolvedComponent, Command {
  val instance: C

  val type: KClass<out C>
    get() = instance::class

  override val parentComponent: ResolvedCommand<*>?

  override val qualifiedName: String
    get() = "command " + valueSource.reference
}
