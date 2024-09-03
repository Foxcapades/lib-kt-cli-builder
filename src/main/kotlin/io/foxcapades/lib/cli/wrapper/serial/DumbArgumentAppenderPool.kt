package io.foxcapades.lib.cli.wrapper.serial

import io.foxcapades.lib.cli.wrapper.util.dump
import java.lang.ref.WeakReference

internal object DumbArgumentAppenderPool {
  private const val PoolSize = 4

  private val available = ArrayDeque<WeakReference<DumbArgumentAppender>>(PoolSize)

  fun use(config: CliSerializationConfig, fn: (CliArgumentAppender) -> Unit): String {
    val appender = getAppender(config)
    return try {
      fn(appender)
      appender.buffer.dump()
    } finally {
      returnAppender(appender)
    }
  }

  private fun prunePool() {
    while (available.isNotEmpty() && available.first().get() == null) {
      available.removeFirst()
    }
  }

  private fun returnAppender(appender: DumbArgumentAppender) {
    synchronized(available) {
      prunePool()
      if (available.size < PoolSize)
        available.addLast(WeakReference(appender))
    }
  }

  private fun getAppender(config: CliSerializationConfig): DumbArgumentAppender {
    while (available.isNotEmpty()) {
      return synchronized(available) { available.removeFirstOrNull() }
        ?.get()
        ?.also { it.config = config }
        ?: continue
    }

    return DumbArgumentAppender(config)
  }
}

private class DumbArgumentAppender(override var config: CliSerializationConfig) : AbstractCliArgumentAppender() {
  public override val buffer = StringBuilder(512)
}

