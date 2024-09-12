package io.foxcapades.lib.cli.builder.flag

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("flag.boolean")
class BooleanConstructorsTest {

  @Nested
  @DisplayName("booleanFlag { ... }")
  inner class NoArg {

    @Test
    @DisplayName("configured default is usable")
    fun t1() {
      val foo by booleanFlag { default = true }

      assertTrue(foo)
    }
  }
}
