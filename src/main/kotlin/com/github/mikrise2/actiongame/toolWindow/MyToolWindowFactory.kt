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
import com.github.mikrise2.actiongame.actions.ActionHandler
import com.github.mikrise2.actiongame.actions.ActionService
import com.github.mikrise2.actiongame.services.MyProjectService
import com.intellij.openapi.actionSystem.ActionManager
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

    class MyToolWindow(toolWindow: ToolWindow) {

        private var label: JBLabel = JBLabel()
        private val service = toolWindow.project.service<MyProjectService>()
        private val actionService = toolWindow.project.service<ActionService>()
        private val next = JButton("Next")

        fun getContent() = JBPanel<JBPanel<*>>().apply {
            label = JBLabel(MyBundle.message("randomLabel", "?"))

            add(label)
            add(next)
            updateAction(false)
            next.addActionListener {
                updateAction(false)
            }
        }

        fun updateAction(addButton: Boolean = true) {
            if (addButton) {
                label.text = "Congratulations!"
            } else
                getNextAction()
        }

        private fun getNextAction() {
            val action = actionService.getRandomAction()
            label.text = action.templatePresentation.description
            val actionId = ActionManager.getInstance().getId(action) ?: error("No action found")
            ActionHandler(actionId, this@MyToolWindow)
        }
    }
}
