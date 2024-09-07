package io.foxcapades.lib.cli.builder.arg

import io.foxcapades.lib.cli.builder.component.BaseComponentOptions
import io.foxcapades.lib.cli.builder.arg.filter.ArgumentPredicate
import io.foxcapades.lib.cli.builder.arg.format.ArgumentFormatter
import io.foxcapades.lib.cli.builder.util.properties.MutableProperty
import kotlin.reflect.KClass

abstract class BaseArgOptions<T : Any, O : T?>(type: KClass<out T>) : BaseComponentOptions<T>(type) {
  var required by MutableProperty<Boolean>()
  var default by MutableProperty<O>()
  var filter by MutableProperty<ArgumentPredicate<out Argument<O>, O>>()
  var shouldQuote by MutableProperty<Boolean>()
  var formatter by MutableProperty<ArgumentFormatter<O>>()
}
