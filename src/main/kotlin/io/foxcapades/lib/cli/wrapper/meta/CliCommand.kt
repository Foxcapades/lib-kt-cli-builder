package io.foxcapades.lib.cli.wrapper.meta

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class CliCommand(
  /**
   * Root CLI command.
   */
  val command: String,

  /**
   * Additional cli command components.
   */
  vararg val extras: String
)