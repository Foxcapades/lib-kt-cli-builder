package io.foxcapades.lib.cli.builder.flag

import io.foxcapades.lib.cli.builder.arg.PathArgument
import java.nio.file.Path

interface PathFlag : ComplexFlag<PathArgument, Path>
