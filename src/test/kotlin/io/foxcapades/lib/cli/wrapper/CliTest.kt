package io.foxcapades.lib.cli.wrapper

import io.foxcapades.lib.cli.wrapper.meta.CliCommand
import io.foxcapades.lib.cli.wrapper.meta.CliFlag
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("E2E")
class CliTest {

  @Nested
  @DisplayName("using @CliCommand")
  inner class WithCliCommand {

    @CliCommand("goodbye")
    private inner class Foo(
      @CliFlag(longForm = "cruel")
      var string: String? = null
    )

    @Test
    fun t1() {
      println(Cli.toCliString(Foo("world")))
    }

  }

}