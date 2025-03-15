package com.github.mikrise2.actiongame.toolWindow

import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPanel
import com.intellij.ui.content.ContentFactory
import com.github.mikrise2.actiongame.MyBundle
import com.github.mikrise2.actiongame.actions.DisableSearchAction
import com.github.mikrise2.actiongame.services.MyProjectService
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.ui.AnActionButton
import javax.swing.JButton


class MyToolWindowFactory : ToolWindowFactory {

    init {
        thisLogger().warn("Don't forget to remove all non-needed sample code files with their corresponding registration entries in `plugin.xml`.")
    }

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val myToolWindow = MyToolWindow(toolWindow)
        val content = ContentFactory.getInstance().createContent(myToolWindow.getContent(), null, false)
        toolWindow.contentManager.addContent(content)
    }

    override fun shouldBeAvailable(project: Project) = true

    class MyToolWindow(val toolWindow: ToolWindow) {

        private val service = toolWindow.project.service<MyProjectService>()

        fun getContent(): JBPanel<JBPanel<*>> {
            toolWindow.setTitleActions(listOf(DisableSearchAction()))
            return JBPanel<JBPanel<*>>().apply {
                val label = JBLabel(MyBundle.message("randomLabel", "?"))

                add(label)
                val button = JButton(MyBundle.message("shuffle")).apply {
                    addActionListener {
                        label.text = MyBundle.message("randomLabel", service.getRandomNumber())
                    }
                }
                add(button)

            }
        }

    }
}
