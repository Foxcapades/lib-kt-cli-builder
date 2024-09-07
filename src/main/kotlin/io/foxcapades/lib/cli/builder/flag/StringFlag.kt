package io.foxcapades.lib.cli.builder.flag

import io.foxcapades.lib.cli.builder.arg.StringArgument

interface StringFlag : ScalarFlag<StringArgument, String>, ComplexFlag<StringArgument, String>
