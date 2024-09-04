package io.foxcapades.lib.cli.wrapper

import io.foxcapades.lib.cli.wrapper.flag.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.test.assertIs

@DisplayName("flag fn")
class FlagConstructorFunctionTest {

  @Nested
  @DisplayName("reified")
  inner class Reified {

    @Nested
    @DisplayName("built-in")
    inner class BuiltIns {

      @Test
      @DisplayName("BigDecimal")
      fun bigDecimal() {
        assertIs<BigDecimalFlag>(flag<BigDecimal> {  })
      }

      @Test
      @DisplayName("BigInteger")
      fun bigInteger() {
        assertIs<BigIntegerFlag>(flag<BigInteger> {  })
      }

      @Test
      @DisplayName("Boolean")
      fun boolean() {
        assertIs<BooleanFlag>(flag<Boolean> {  })
      }

      @Test
      @DisplayName("Byte")
      fun byte() {
        assertIs<ByteFlag>(flag<Byte> {  })
      }

      @Test
      @DisplayName("Char")
      fun char() {
        assertIs<CharFlag>(flag<Char> {  })
      }

      @Test
      @DisplayName("Double")
      fun double() {
        assertIs<DoubleFlag>(flag<Double> {  })
      }

      @Test
      @DisplayName("Float")
      fun float() {
        assertIs<FloatFlag>(flag<Float> {  })
      }

      @Test
      @DisplayName("Int")
      fun int() {
        assertIs<IntFlag>(flag<Int> {  })
      }

      @Test
      @DisplayName("Long")
      fun long() {
        assertIs<LongFlag>(flag<Long> {  })
      }

      @Test
      @DisplayName("Short")
      fun short() {
        assertIs<ShortFlag>(flag<Short> {  })
      }

      @Test
      @DisplayName("String")
      fun string() {
        assertIs<StringFlag>(flag<String> {  })
      }

      @Test
      @DisplayName("UByte")
      fun ubyte() {
        assertIs<UByteFlag>(flag<UByte> {  })
      }

      @Test
      @DisplayName("UInt")
      fun uint() {
        assertIs<UIntFlag>(flag<UInt> {  })
      }

      @Test
      @DisplayName("ULong")
      fun ulong() {
        assertIs<ULongFlag>(flag<ULong> {  })
      }

      @Test
      @DisplayName("UShort")
      fun ushort() {
        assertIs<UShortFlag>(flag<UShort> {  })
      }
    }
  }
}
