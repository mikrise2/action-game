package com.github.mikrise2.actiongame.actions

import com.intellij.ide.actions.SearchEverywhereBaseAction
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages

class DisableSearchAction : AnAction() {
    companion object {
        val actionsToDisable = ActionManager.getInstance().getActionIdList("").associateWith {
            ActionManager.getInstance().getAction(it)
        }.filter { (_, action) -> action is SearchEverywhereBaseAction }
    }

    override fun actionPerformed(event: AnActionEvent) {
        if (ActionManager.getInstance().getAction("SearchEverywhere") != null) {
            Messages.showErrorDialog(event.project, "\"Search Everywhere\" disabled", "\"Search Everywhere\" Disabled")

            for ((actionId, _) in actionsToDisable) {
                ActionManager.getInstance().unregisterAction(actionId)
            }
        } else {
            Messages.showErrorDialog(event.project, "\"Search Everywhere\" enabled", "\"Search Everywhere\" Enabled")
            for ((actionId, action) in actionsToDisable) {
                ActionManager.getInstance().registerAction(actionId, action)
            }
        }
    }
}
