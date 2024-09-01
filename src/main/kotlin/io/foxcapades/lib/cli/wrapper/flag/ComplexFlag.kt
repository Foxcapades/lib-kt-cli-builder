package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.Flag
import io.foxcapades.lib.cli.wrapper.arg.ComplexArgument

interface ComplexFlag<A: ComplexArgument<V>, V> : Flag<A, V>