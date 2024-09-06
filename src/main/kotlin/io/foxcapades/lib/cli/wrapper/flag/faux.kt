package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.ResolvedFlag
import io.foxcapades.lib.cli.wrapper.arg.FauxArgument
import io.foxcapades.lib.cli.wrapper.meta.CliFlagAnnotation
import io.foxcapades.lib.cli.wrapper.reflect.AnnotatedPropertyReference
import io.foxcapades.lib.cli.wrapper.serial.CliAppender
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

internal class FauxFlag<T : Any>(
  internal val instance: T,
  override val annotation: CliFlagAnnotation,
  override val property: KProperty1<T, *>,
  override val type: KClass<out T>,
) : ResolvedFlag<T, Any?>, AnnotatedPropertyReference<T, Any?, CliFlagAnnotation> {
  override val hasLongForm
    get() = annotation.hasLongForm

  override val longForm
    get() = annotation.longForm

  override val hasShortForm
    get() = annotation.hasShortForm

  override val shortForm
    get() = annotation.shortForm

  override val argument by lazy { FauxArgument(this) }

  override val isRequired
    get() = annotation.required

  override fun writeToString(builder: CliAppender) {
    if (!hasLongForm || builder.config.preferredFlagForm.isShort && hasShortForm) {
      builder.putShortFlag(shortForm, true)
    } else {
      builder.putLongFlag(longForm, true)
    }

    builder.putArgument(argument)
  }
}
