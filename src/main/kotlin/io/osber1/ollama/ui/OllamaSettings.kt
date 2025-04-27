package io.osber1.ollama.ui

import com.intellij.openapi.options.Configurable
import javax.swing.JComponent

class OllamaSettings : Configurable {
    private var settingsComponent: OllamaSettingsComponent? = null

    override fun getDisplayName(): String = "Ollama Proxy Settings"

    override fun createComponent(): JComponent {
        settingsComponent = OllamaSettingsComponent()
        return settingsComponent!!.panel
    }

    override fun isModified(): Boolean {
        val settings = OllamaSettingsState.Companion.instance
        return settingsComponent?.getApiKey() != settings.apiKey || settingsComponent?.getPort() != settings.port
    }

    override fun apply() {
        val settings = OllamaSettingsState.Companion.instance
        settings.apiKey = settingsComponent?.getApiKey() ?: ""
        settings.port = settingsComponent?.getPort() ?: 11434
    }

    override fun reset() {
        val settings = OllamaSettingsState.Companion.instance
        settingsComponent?.setApiKey(settings.apiKey)
        settingsComponent?.setPort(settings.port)
    }

    override fun disposeUIResources() {
        settingsComponent = null
    }
}

