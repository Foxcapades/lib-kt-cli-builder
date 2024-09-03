package io.foxcapades.lib.cli.wrapper.util

class NoSuchValueException : RuntimeException("attempted to unwrap a value that had not been set")