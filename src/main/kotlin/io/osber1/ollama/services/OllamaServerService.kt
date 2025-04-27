package io.osber1.ollama.services

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.Service.Level
import com.intellij.openapi.project.Project
import io.osber1.ollama.Server
import io.osber1.ollama.provider.LLMProvider
import io.osber1.ollama.ui.OllamaSettingsState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import java.io.IOException
import java.net.Socket

@Service(Level.PROJECT)
class OllamaServerService(private val project: Project) {
    private var server: Server? = null
    private var serverJob: Job? = null
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val logger = LoggerFactory.getLogger(OllamaServerService::class.java)

    fun startServer(provider: LLMProvider) {
        if (isRunning()) return

        val port = OllamaSettingsState.instance.port
        if (isPortInUse(port)) {
            logger.error("Port $port is already in use. Cannot start server.")
            throw IllegalStateException("Port $port is already in use. Please stop any other services using this port.")
        }

        serverJob = scope.launch {
            try {
                server = Server(provider)
                server?.start()
                logger.info("Server started successfully on port $port")
            } catch (e: Exception) {
                logger.error("Failed to start server", e)
                server = null
                throw e
            }
        }
    }

    fun stopServer() {
        runBlocking {
            try {
                val port = OllamaSettingsState.instance.port
                logger.info("Stopping server...")
                server?.stop()
                serverJob?.cancelAndJoin()
                server = null
                serverJob = null

                delay(1000) // Give some time for port to be released

                if (isPortInUse(port)) {
                    logger.error("Port $port still in use after server stop")
                } else {
                    logger.info("Server port successfully released")
                }
            } catch (e: Exception) {
                logger.error("Error stopping server", e)
            }
        }
    }

    private fun isPortInUse(port: Int): Boolean {
        return try {
            Socket("localhost", port).use { true }
        } catch (e: IOException) {
            false
        }
    }

    fun isRunning(): Boolean = serverJob?.isActive == true
}
