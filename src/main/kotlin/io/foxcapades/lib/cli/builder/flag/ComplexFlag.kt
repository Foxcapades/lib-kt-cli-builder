package io.foxcapades.lib.cli.builder.flag

import io.foxcapades.lib.cli.builder.arg.ComplexArgument

interface ComplexFlag<A: ComplexArgument<V>, V> : Flag<A, V>
