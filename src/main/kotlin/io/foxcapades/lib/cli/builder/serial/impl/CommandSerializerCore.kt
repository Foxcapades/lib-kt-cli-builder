package io.foxcapades.lib.cli.builder.serial.impl

import io.foxcapades.lib.cli.builder.arg.*
import io.foxcapades.lib.cli.builder.command.Command
import io.foxcapades.lib.cli.builder.flag.*
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.*
import io.foxcapades.lib.cli.builder.util.reflect.*
import io.foxcapades.lib.cli.builder.util.then
import kotlin.reflect.*
import io.foxcapades.lib.cli.builder.command.CliCommand as ComAnn

@Deprecated("needs to be dissolved")
internal class CommandSerializerCore(
  private val instance: Any,
  private val config:   CliSerializationConfig
) {
  private val type: KClass<out Any> = instance::class


  private val commandName: String

  private val components: Iterator<Any>

  init {
    if (instance is Command) {
      instance.getCliCallComponents(config).then { (name, stream) ->
        commandName = name
        components  = stream.iterator()
      }
    } else {
      (type.annotations.findInstance<ComAnn>()
        // TODO: this should be its own exception type
        ?: throw IllegalArgumentException(
          "given type ${type.safeName} cannot be converted into a cli" +
            " string, must be annotated with @CliCommand and/or implement" +
            " CliCallSerializable"))
        .then {
          commandName = it.command
          components  = filterUsableMembers()
        }
    }
  }

  // region Iterator

  fun serializeToIterator() = iterator {
    yield(commandName)

    val serializer = LazyCliAppenderImpl<Any>(components, config)
    while (serializer.hasNext()) {
      val segment = serializer.next()

      yield(segment)
    }
  }

  // endregion Iterator

  // region Stringify

  fun serializeToString(preSize: Int) =
    StringCliAppenderImpl(commandLeader, filterUsableProperties(), config, preSize).toString()

  // endregion Stringify

  // region Component Resolution


  // endregion Component Resolution

  // region Validation


  // endregion Validation

}
