package io.osber1.ollama.ui

import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import javax.swing.JPanel
import javax.swing.text.AttributeSet
import javax.swing.text.PlainDocument

class OllamaSettingsComponent {
    private val apiKeyField = JBTextField()
    private val portField = JBTextField("11434").apply {
        document = object : PlainDocument() {
            override fun insertString(offs: Int, str: String?, a: AttributeSet?) {
                if (str == null) return
                val newValue = (text + str).filter { it.isDigit() }
                if (newValue.length <= 5 && newValue.toIntOrNull() in 1..65535) {
                    super.insertString(offs, str, a)
                }
            }
        }
        columns = 5
    }

    val panel: JPanel = FormBuilder.createFormBuilder()
        .addLabeledComponent(JBLabel("Anthropic API Key: "), apiKeyField, 1, false)
        .addLabeledComponent(JBLabel("Proxy Port: "), portField, 1, false)
        .addComponentFillVertically(JPanel(), 0)
        .panel

    fun getApiKey(): String = apiKeyField.text
    fun getPort(): Int = portField.text.toIntOrNull() ?: 11434

    fun setApiKey(apiKey: String) {
        apiKeyField.text = apiKey
    }

    fun setPort(port: Int) {
        portField.text = port.toString()
    }
}
