package io.foxcapades.lib.cli.builder.flag

import io.foxcapades.lib.cli.builder.arg.FileArgument
import java.io.File

interface FileFlag : ComplexFlag<FileArgument, File>
