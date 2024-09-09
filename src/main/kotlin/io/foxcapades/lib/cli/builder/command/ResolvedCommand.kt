package io.foxcapades.lib.cli.builder.command

import io.foxcapades.lib.cli.builder.component.ResolvedComponent
import kotlin.reflect.KClass

interface ResolvedCommand<T : Any> : ResolvedComponent, Command {
  val instance: T

  val type: KClass<out T>
    get() = instance::class

  override val parentComponent: ResolvedCommand<*>?
}
