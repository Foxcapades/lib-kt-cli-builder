package io.foxcapades.lib.cli.builder

import io.foxcapades.kt.prop.delegation.NoSuchValueException

open class UnsetArgumentDefaultException(msg: String) : NoSuchValueException(msg) {
  constructor() : this("attempted to fetch the default value for an argument that had none")
}
