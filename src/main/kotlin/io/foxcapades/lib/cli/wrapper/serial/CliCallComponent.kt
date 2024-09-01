package io.foxcapades.lib.cli.wrapper.serial

interface CliCallComponent {
  fun writeToString(builder: CliStringBuilder)
}