package io.foxcapades.lib.cli.wrapper.shell

import io.foxcapades.lib.cli.wrapper.util.dump
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Shell: SH")
class SHTest {

  private val reservedASCII = charArrayOf(
    // NUL     SOH       STX       ETX       EOT       ENQ       ACK
    '\u0000', '\u0001', '\u0002', '\u0003', '\u0004', '\u0005', '\u0006',
    // BEL     BS        HT        LF        VT        FF        CR
    '\u0007', '\u0008', '\u0009', '\u000A', '\u000B', '\u000C', '\u000D',
    // SO      SI        DLE       DC1       DC2       DC3       DC4
    '\u000E', '\u000F', '\u0010', '\u0011', '\u0012', '\u0013', '\u0014',
    // NAK     SYN       ETB       CAN       EM        SUB       ESC
    '\u0015', '\u0016', '\u0017', '\u0018', '\u0019', '\u001A', '\u001B',
    // FS      GS        RS        US
    '\u001C', '\u001D', '\u001E', '\u001F',

    ' ', '!', '"', '#', '$', '&', '\'', '(', ')', '*', ',', ';', '<', '>', '?',
    '[', '\\', ']', '^', '`', '{', '|', '}', '~',

    // DEL
    '\u007F'
  )

  lateinit var target: SH

  @BeforeEach
  fun newSH() {
    target = SH()
  }

  @Nested
  @DisplayName("escapeArgumentChar")
  inner class EscapeArgumentChar {

    @Nested
    @DisplayName("when in quotes")
    inner class Quoted {

      lateinit var buffer: StringBuilder

      @BeforeEach
      fun newBuffer() {
        buffer = StringBuilder()
      }

      @Test
      @DisplayName("only escapes apostrophes")
      fun only_escapes_apostrophes() {
        for (c in reservedASCII) {
          target.escapeArgumentChar(c, true, buffer::append)
          if (c == '\'') {
            assertEquals("'\"'\"'", buffer.dump())
          } else {
            assertEquals("$c", buffer.dump())
          }
        }
      }
    }
  }
}
