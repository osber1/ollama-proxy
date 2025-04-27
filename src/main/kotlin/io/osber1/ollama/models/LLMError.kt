package io.osber1.ollama.models

sealed class LLMError : Exception() {
    data class ApiError(override val message: String) : LLMError()
    data class ParseError(override val message: String) : LLMError()
}
