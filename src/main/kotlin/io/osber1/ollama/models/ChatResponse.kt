package io.osber1.ollama.models

data class ChatResponse(
    val content: String,
    val promptTokens: Int?,
    val completionTokens: Int?,
    val model: String,
    val createdAt: String
)