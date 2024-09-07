package io.foxcapades.lib.cli.builder.component

/**
 * Base type representing a CLI component annotation instance.
 *
 * @since 1.0.0
 */
interface CliComponentAnnotation {

  /**
   * Indicates whether the annotation instance was marked as being required.
   */
  val required: Boolean
}
