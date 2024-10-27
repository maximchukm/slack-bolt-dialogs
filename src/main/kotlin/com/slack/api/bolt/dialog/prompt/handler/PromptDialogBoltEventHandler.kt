package com.slack.api.bolt.dialog.prompt.handler

import com.slack.api.app_backend.events.payload.EventsApiPayload
import com.slack.api.bolt.context.builtin.EventContext
import com.slack.api.bolt.dialog.Dialog
import com.slack.api.bolt.dialog.context.DialogContextService
import com.slack.api.bolt.dialog.prompt.PromptDialog
import com.slack.api.bolt.dialog.prompt.listener.PromptDialogListener
import com.slack.api.bolt.handler.BoltEventHandler
import com.slack.api.bolt.response.Response
import com.slack.api.model.event.AppMentionEvent

class PromptDialogBoltEventHandler(
    private val dialogContextService: DialogContextService,
    dialogListeners: List<PromptDialogListener>
) : BoltEventHandler<AppMentionEvent> {

    private val listeners: Map<Class<out Dialog>, PromptDialogListener> =
        dialogListeners.associateBy { it.dialogClass }

    override fun apply(payload: EventsApiPayload<AppMentionEvent>, context: EventContext): Response {
        val dialogContext = dialogContextService.restoreContext(context.requestUserId) ?: return context.ack()
        val dialog = dialogContext.dialogClass.getConstructor().newInstance()

        if (dialog is PromptDialog) {
            dialog.restoreFromContext(dialogContext)

            dialog.nextPrompt()
                ?.run {
                    answer = payload.event.text.replace(Regex("<.*>"), "")
                    listeners[dialog.javaClass]?.onPromptCompleted(dialog, this, context)
                }

            dialog.nextPrompt()
                ?.run {
                    context.say(question)
                }
                ?: listeners[dialog.javaClass]?.onDialogCompleted(dialog, context)
        }

        dialog.getDialogContext()
            ?.run { dialogContextService.saveContext(this) }

        return context.ack()
    }

}