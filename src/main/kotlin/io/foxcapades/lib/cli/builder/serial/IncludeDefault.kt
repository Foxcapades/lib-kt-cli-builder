package io.foxcapades.lib.cli.builder.serial

/**
 * Config setting indicating when a default value should be included in
 * generated CLI call output.
 */
enum class IncludeDefault {
  /**
   * [Always] indicates that the value should always be included in the CLI call
   * output regardless of whether it is defaulted or not.
   */
  Always,

  /**
   * [Never] indicates that the value should never be included in the CLI call
   * output if it is equal to its set default regardless of whether it was set
   * explicitly or not.
   */
  Never,

  /**
   * [IfSetExplicitly] indicates that the value should only be included in the
   * CLI call if it was explicitly set to its default value, not if it was left
   * unset.
   */
  IfSetExplicitly,
}
