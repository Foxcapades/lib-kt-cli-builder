package io.foxcapades.lib.cli.builder.serial

import io.foxcapades.lib.cli.builder.component.ResolvedComponentOld
import io.foxcapades.lib.cli.builder.flag.CliFlag
import io.foxcapades.lib.cli.builder.flag.ResolvedFlagOld
import io.foxcapades.lib.cli.builder.flag.flag
import io.foxcapades.lib.cli.builder.flag.stringFlag
import io.foxcapades.lib.cli.builder.serial.impl.CommandSerializerCore
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.TestInstance
import org.mockito.kotlin.mock
import kotlin.reflect.KFunction2
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.jvm.isAccessible
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@DisplayName("CommandSerializerImpl")
class CommandSerializerCoreTest {

  @Nested
  @DisplayName("siftProperty")
  @TestInstance(TestInstance.Lifecycle.PER_CLASS)
  inner class SiftProperty {

    private lateinit var method: KFunction2<CommandSerializerCore<*>, KProperty1<*, *>, ResolvedComponentOld<*, Any?>?>

    private fun newConfig() = mock<CliSerializationConfig>()

    @Suppress("UNCHECKED_CAST")
    private fun <T : Any, V> flagMethod() =
      method as KFunction2<CommandSerializerCore<T>, KProperty1<T, *>, ResolvedFlagOld<T, V>?>

    @BeforeAll
    fun beforeAll() {
      val refs = CommandSerializerCore::class.declaredFunctions
        .filter { it.name == "siftProperty" }

      assertEquals(1, refs.size, "expected exactly 1 siftProperty method")

      @Suppress("UNCHECKED_CAST")
      method = refs[0] as KFunction2<CommandSerializerCore<*>, KProperty1<*, *>, ResolvedComponentOld<*, Any?>?>
      method.isAccessible = true
    }

    @Nested
    @DisplayName("with delegated flag property")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class DelegateOnly {

      inner class DummyType {
        var property by flag<String>()
      }

      private fun newTarget(init: DummyType.() -> Unit = {}) =
        CommandSerializerCore(DummyType().apply(init), newConfig())

      @Test
      @DisplayName("returns the delegate instance")
      fun t1() {
        val target = newTarget { property = "hi" }
        val result = assertNotNull(flagMethod<DummyType, String>()(target, DummyType::property.apply { isAccessible = true }))
        assertEquals("hi", result.get())
      }
    }

    @Nested
    @DisplayName("with annotated flag property")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class AnnotationOnly {

      inner class DummyType {
        @CliFlag
        var property: String = ""
      }

      private fun newTarget(init: DummyType.() -> Unit = {}) =
        CommandSerializerCore(DummyType().apply(init), newConfig())

      @Test
      @DisplayName("returns the annotated instance")
      fun t1() {
        val target = newTarget { property = "hi" }
        val result = assertNotNull(flagMethod<DummyType, String>()(target, DummyType::property.apply { isAccessible = true }))
        assertEquals("hi", result.get())
      }
    }

    @Nested
    @DisplayName("with annotated raw property")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class RawOnly {

      inner class DummyType {
        val property = stringFlag()
      }

      private fun newTarget(init: DummyType.() -> Unit = {}) =
        CommandSerializerCore(DummyType().apply(init), newConfig())

      @Test
      @DisplayName("returns the raw instance")
      fun t1() {
        val target = newTarget { property.set("hi") }
        val result = assertNotNull(flagMethod<DummyType, String>()(target, DummyType::property.apply { isAccessible = true }))
        assertEquals("hi", result.get())
      }
    }
  }
}
