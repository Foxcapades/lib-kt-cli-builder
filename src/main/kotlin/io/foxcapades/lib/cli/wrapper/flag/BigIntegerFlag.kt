package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.BigIntegerArgument
import java.math.BigInteger

interface BigIntegerFlag : ScalarFlag<BigIntegerArgument, BigInteger>