package io.foxcapades.lib.cli.builder.flag

import io.foxcapades.lib.cli.builder.arg.ScalarArgument

interface ScalarFlag<A: ScalarArgument<V>, V> : Flag<A, V>
