package io.foxcapades.lib.cli.builder.arg.ref.impl

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.arg.impl.CliArgumentAnnotationImpl
import io.foxcapades.lib.cli.builder.arg.ref.UnlinkedResolvedArgument
import io.foxcapades.lib.cli.builder.flag.ref.ResolvedFlag

internal open class AnnotatedFlagValueArgument<V>(
  annotation: CliArgumentAnnotationImpl,
  parent:     ResolvedFlag<V>,
  instance:   Argument<V>,
)
  : UnlinkedResolvedArgument<V>
  , BaseAnnotatedValueArgument<ResolvedFlag<V>, V>(annotation, parent, instance)
