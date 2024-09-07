package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.FileArgument
import java.io.File

interface FileFlag : ComplexFlag<FileArgument, File>
