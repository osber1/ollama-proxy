package io.osber1.ollama.provider

import io.osber1.ollama.models.ChatOptions
import io.osber1.ollama.models.ChatResponse
import io.osber1.ollama.models.Message
import io.osber1.ollama.models.Model
import kotlinx.serialization.json.JsonObject

interface LLMProvider {
    suspend fun chat(messages: List<Message>, options: ChatOptions): Result<ChatResponse>
    fun getModels(): List<Model>
    suspend fun getModelDetails(modelName: String): Result<JsonObject>
}
