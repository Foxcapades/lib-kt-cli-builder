package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.BigDecimalArgument
import java.math.BigDecimal

interface BigDecimalFlag : ScalarFlag<BigDecimalArgument, BigDecimal>