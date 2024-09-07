package io.foxcapades.lib.cli.builder

import io.foxcapades.lib.cli.builder.util.NoSuchDefaultValueException

open class UnsetArgumentDefaultException(msg: String) : NoSuchDefaultValueException(msg) {
  constructor() : this("attempted to fetch the default value for an argument that had none")
}
