package io.foxcapades.lib.cli.builder.util.properties

@JvmInline
internal value class BooleanProperty private constructor(private val value: Boolean) : Property<Boolean> {
  override val isSet get() = true
  override fun get() = value

  companion object {
    @JvmStatic
    val True = BooleanProperty(true)

    @JvmStatic
    val False = BooleanProperty(false)
  }
}
