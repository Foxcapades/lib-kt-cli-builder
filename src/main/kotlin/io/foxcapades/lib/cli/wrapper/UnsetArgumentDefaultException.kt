package io.foxcapades.lib.cli.wrapper

class UnsetArgumentDefaultException(msg: String = "attempted to fetch the default value for an argument that had none")
  : RuntimeException(msg)