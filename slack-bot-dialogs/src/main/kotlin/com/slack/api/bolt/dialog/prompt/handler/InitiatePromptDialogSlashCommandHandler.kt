package com.slack.api.bolt.dialog.prompt.handler

import com.slack.api.bolt.context.builtin.SlashCommandContext
import com.slack.api.bolt.dialog.context.DialogContext
import com.slack.api.bolt.dialog.context.DialogContextService
import com.slack.api.bolt.dialog.prompt.PromptDialog
import com.slack.api.bolt.handler.builtin.SlashCommandHandler
import com.slack.api.bolt.request.builtin.SlashCommandRequest
import com.slack.api.bolt.response.Response

class InitiatePromptDialogSlashCommandHandler(
    private val dialogClass: Class<out PromptDialog>,
    private val dialogContextService: DialogContextService
) : SlashCommandHandler {

    override fun apply(req: SlashCommandRequest, context: SlashCommandContext): Response {
        val dialogContext = DialogContext(dialogClass, context.requestUserId)
        val dialog = dialogClass.getConstructor().newInstance()
        dialog.restoreFromContext(dialogContext)

        dialog.nextPrompt()
            ?.run {
                context.say(question)
            }

        dialogContextService.saveContext(dialogContext)
        return context.ack()
    }

}