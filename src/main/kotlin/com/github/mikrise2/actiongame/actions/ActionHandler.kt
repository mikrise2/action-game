package com.github.mikrise2.actiongame.actions

import com.github.mikrise2.actiongame.toolWindow.MyToolWindowFactory
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.ex.AnActionListener
import com.intellij.openapi.application.ApplicationManager
import com.intellij.util.messages.MessageBusConnection
import com.intellij.util.messages.Topic

class ActionHandler(val actionId: String, val toolWindow: MyToolWindowFactory.MyToolWindow){

    private val messageBusConnections: MutableList<MessageBusConnection> = mutableListOf()

    init {
        check()
    }

    private fun check(){
        val actionListener = object : AnActionListener {
            override fun beforeActionPerformed(anAction: AnAction, event: AnActionEvent) {
                val actionId = ActionManager.getInstance().getId(anAction)
                    ?: anAction.javaClass.name
                println("----")
                println(actionId)
                println("&&&&&&")
                println(this@ActionHandler.actionId)
                println("----")
                if (this@ActionHandler.actionId == actionId) {
                    this@ActionHandler.toolWindow.updateAction()
                }
            }
        }
        actionListener.connect(AnActionListener.TOPIC)
    }

    private fun <L : Any> L.connect(topic: Topic<L>) {
        val connection = ApplicationManager.getApplication().messageBus.connect()
        messageBusConnections.add(connection)
        connection.subscribe(topic, this)
    }

}