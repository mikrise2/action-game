package com.github.mikrise2.actiongame.actions

import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project

@Service(Service.Level.PROJECT)
class ActionService(val project: Project) {

    fun getRandomAction(): AnAction {
        val actionManager = ActionManager.getInstance()
        val actionIds = actionManager.getActionIdList("")
        val actions = actionIds.map { actionManager.getAction(it) }
        return actions.filter {
            it.templatePresentation.description != null &&
                    it.templatePresentation.description.isNotBlank()
                    && it.javaClass.name.startsWith("com.intellij")
        }.random()
    }
}