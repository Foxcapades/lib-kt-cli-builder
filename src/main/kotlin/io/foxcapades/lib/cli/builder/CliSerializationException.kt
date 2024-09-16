package io.foxcapades.lib.cli.builder

open class CliSerializationException : RuntimeException {
  constructor() : super()

  constructor(message: String) : super(message)

  constructor(message: String, cause: Throwable) : super(message, cause)

  constructor(cause: Throwable) : super(cause)

  constructor(message: String, cause: Throwable, enableSuppression: Boolean, writableStackTrace: Boolean) : super(
    message,
    cause,
    enableSuppression,
    writableStackTrace
  )
}
