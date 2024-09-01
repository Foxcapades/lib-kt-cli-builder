package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.LongArgument

interface LongFlag : ScalarFlag<LongArgument, Long>