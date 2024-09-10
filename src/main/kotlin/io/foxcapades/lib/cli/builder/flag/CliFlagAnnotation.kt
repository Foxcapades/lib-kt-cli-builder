package io.foxcapades.lib.cli.builder.flag

import io.foxcapades.lib.cli.builder.arg.CliArgumentAnnotation
import io.foxcapades.lib.cli.builder.component.CliComponentAnnotation
import io.foxcapades.lib.cli.builder.flag.filter.FlagPredicate
import kotlin.reflect.KClass

interface CliFlagAnnotation : CliComponentAnnotation<CliFlag> {
  /**
   * Indicates whether the annotation instance was marked as being required.
   */
  val required: CliFlag.Toggle

  val hasLongForm: Boolean

  val longForm: String

  val hasShortForm: Boolean

  val shortForm: Char

  val hasFilter: Boolean

  val filter: KClass<out FlagPredicate<*, *, *>>

  val argument: CliArgumentAnnotation
}
