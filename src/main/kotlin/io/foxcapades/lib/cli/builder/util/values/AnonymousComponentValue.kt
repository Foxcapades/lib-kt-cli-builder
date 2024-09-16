package io.foxcapades.lib.cli.builder.util.values

import kotlin.reflect.KClass
import io.foxcapades.lib.cli.builder.command.Command
import io.foxcapades.lib.cli.builder.component.CliCallComponent

/**
 * This value source is for situations where cli-builder has been handed a
 * [CliCallComponent] instance without any additional context.
 *
 * This will typically only happen if a component value is returned from
 * [Command.getCliCallComponents].
 *
 * In this situation we don't know anything about the actual source for the
 * value, and it may not even have one.
 */
internal object AnonymousComponentValue : ValueSource {
  override val name
    get() = "???"

  override val hasName: Boolean
    get() = false

  override val reference
    get() = "???::???"

  override val kind
    get() = ValueSource.Kind.Anonymous

  override val containerType: KClass<*>?
    get() = null

  override val containerInstance: Any?
    get() = null
}
