package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.Flag
import io.foxcapades.lib.cli.wrapper.arg.ScalarArgument

interface ScalarFlag<A: ScalarArgument<V>, V> : Flag<A, V>
