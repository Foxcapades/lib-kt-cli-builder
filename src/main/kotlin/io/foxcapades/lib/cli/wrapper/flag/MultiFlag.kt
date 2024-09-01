package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.MultiArgument

interface MultiFlag<V> : ComplexFlag<MultiArgument<V>, Iterable<V>>