package io.foxcapades.lib.cli.builder

import io.foxcapades.lib.cli.builder.command.CliCommand
import io.foxcapades.lib.cli.builder.command.CliCommandComponents
import io.foxcapades.lib.cli.builder.command.Command
import io.foxcapades.lib.cli.builder.flag.CliFlag
import io.foxcapades.lib.cli.builder.flag.pathFlag
import io.foxcapades.lib.cli.builder.flag.stringFlag
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.io.path.Path
import kotlin.test.assertEquals

@DisplayName("E2E")
class CliTest {

  @Nested
  @DisplayName("using @CliCommand")
  inner class WithCommand {

    @Nested
    @DisplayName("with annotated flags")
    inner class WithAnnotatedFlags {
      @CliCommand("goodbye")
      private inner class Foo(
        @CliFlag(longForm = "cruel")
        var string: String? = null
      )

      @Test
      fun t1() {
        assertEquals("goodbye --cruel='world'", Cli.toCliString(Foo("world")))
      }
    }

    @Nested
    @DisplayName("with delegate flags")
    inner class WithDelegateFlags {
      @CliCommand("goodbye")
      private inner class Foo {
        var cruel by stringFlag { longForm = "cruel" }
      }

      @Test
      fun t1() {
        assertEquals("goodbye", Cli.toCliString(Foo()))
        assertEquals("goodbye --cruel='world'", Cli.toCliString(Foo().apply { cruel = "world" }))
      }
    }

  }

  @Nested
  @DisplayName("implementing Command")
  inner class ImplementingCommand {

    private inner class Foo : Command {
      @CliFlag("input", 'i')
      var input by pathFlag()

      override fun getCliCallComponents(config: CliSerializationConfig) =
        "my-command" to listOf(::input)
    }

    @Test
    fun t1() {
      val foo = Foo()
      foo.input = Path("hello/world")

      assertEquals("my-command --input='hello/world'", Cli.toCliString(foo))
    }
  }
}
