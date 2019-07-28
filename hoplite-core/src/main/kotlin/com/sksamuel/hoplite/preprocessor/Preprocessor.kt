package com.sksamuel.hoplite.preprocessor

/**
 * Takes a raw config value and processes it.
 */
interface Preprocessor {
  fun process(value: String): String
}

object EnvVarPreprocessor : Preprocessor {
  private val regex = "\\$\\{(.*?)}".toRegex()
  override fun process(value: String): String = regex.replace(value) {
    val key = it.groupValues[1]
    System.getenv(key) ?: ""
  }
}

abstract class PrefixProcessor(private val prefix: String) : Preprocessor {
  abstract fun handle(value: String): String
  override fun process(value: String): String =
      if (value.startsWith(prefix)) handle(value) else value
}
