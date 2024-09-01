package io.foxcapades.lib.cli.wrapper

import io.foxcapades.lib.cli.wrapper.utils.safeName
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

class IllegalFlagBindingException private constructor(
  val annotation: Annotation,
  val property: KProperty1<*, *>,
  val type: KClass<*>,
  val errorType: BindingErrorType,
) : RuntimeException(errorType.message(property, type)) {

  enum class BindingErrorType {
    RequiredNonNullableField;

    fun message(property: KProperty1<*, *>, type: KClass<*>) = when (this) {
      RequiredNonNullableField -> "field %s.%s is annotated as representing a required flag but is not nullable"
        .format(type.safeName, property.name)
    }
  }

  companion object {
    @JvmStatic
    fun requiredNonNullable(annotation: Annotation, property: KProperty1<*, *>, type: KClass<*>) =
      IllegalFlagBindingException(annotation, property, type, BindingErrorType.RequiredNonNullableField)
  }
}