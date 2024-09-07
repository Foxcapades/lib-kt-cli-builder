package io.foxcapades.lib.cli.builder.flag

import io.foxcapades.lib.cli.builder.arg.BigDecimalArgument
import java.math.BigDecimal

interface BigDecimalFlag : ScalarFlag<BigDecimalArgument, BigDecimal>
