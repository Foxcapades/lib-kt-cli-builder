package io.foxcapades.lib.cli.builder

import io.foxcapades.lib.cli.builder.arg.format.ArgumentFormatter
import io.foxcapades.lib.cli.builder.command.CliCommand
import io.foxcapades.lib.cli.builder.command.Command
import io.foxcapades.lib.cli.builder.flag.CliFlag
import io.foxcapades.lib.cli.builder.flag.pathFlag
import io.foxcapades.lib.cli.builder.flag.stringFlag
import io.foxcapades.lib.cli.builder.flag.toggleFlag
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.io.path.Path
import kotlin.test.Ignore
import kotlin.test.assertEquals

@DisplayName("E2E")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CliTest {

  @Nested
  @DisplayName("using @CliCommand")
  @TestInstance(TestInstance.Lifecycle.PER_CLASS)
  inner class WithCommand {

    @Nested
    @DisplayName("with annotated flags")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
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
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
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
  @TestInstance(TestInstance.Lifecycle.PER_CLASS)
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

  @Nested
  @DisplayName("delegates overriding annotated interface types")
  @TestInstance(TestInstance.Lifecycle.PER_CLASS)
  inner class DelegatesOverrideInterfaceTypes {
    private inner class FooImpl : Foo {
      override var foo by stringFlag { formatter = ArgumentFormatter { it.uppercase() } }
      override var bar by toggleFlag()
    }

    // FIXME: nested delegation is not supported by kotlin, DOCUMENT THIS IN THE README AS A LIMITATION!
    @Test
    fun t1() {
      val foo = FooImpl().also { it.foo = "hello"; it.bar = false }

      assertEquals("test-command 'fungus' --foo='HELLO'", Cli.toCliString(foo as Foo))
    }
  }

  @CliCommand("test-command", "fungus")
  interface Foo {
    @CliFlag("foo", 'f')
    val foo: String?

    @CliFlag("bar", 'b')
    val bar: Boolean?
  }
}
