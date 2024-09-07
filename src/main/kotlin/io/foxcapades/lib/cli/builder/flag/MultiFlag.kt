package io.foxcapades.lib.cli.builder.flag

import io.foxcapades.lib.cli.builder.arg.MultiArgument

interface MultiFlag<A : MultiArgument<I, V>, I : Iterable<V>, V> : ComplexFlag<A, I>
