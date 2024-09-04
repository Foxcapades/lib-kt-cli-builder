package io.foxcapades.lib.cli.wrapper

class UnsetArgumentDefaultException(msg: String) : RuntimeException(msg) {
  constructor() : this("attempted to fetch the default value for an argument that had none")
}
