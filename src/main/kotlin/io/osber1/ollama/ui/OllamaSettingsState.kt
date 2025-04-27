package io.osber1.ollama.ui

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

@State(
    name = "io.osber1.ollama.ui.OllamaSettingsState",
    storages = [Storage("OllamaSettingsPlugin.xml")]
)
class OllamaSettingsState : PersistentStateComponent<OllamaSettingsState> {
    var apiKey: String = ""
    var port: Int = 11434

    override fun getState(): OllamaSettingsState = this

    override fun loadState(state: OllamaSettingsState) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        val instance: OllamaSettingsState
            get() = ApplicationManager.getApplication().getService(OllamaSettingsState::class.java)
    }
}
