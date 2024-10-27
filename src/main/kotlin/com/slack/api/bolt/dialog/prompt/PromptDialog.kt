package com.slack.api.bolt.dialog.prompt

import com.slack.api.bolt.dialog.Dialog
import com.slack.api.bolt.dialog.context.DialogContext

abstract class PromptDialog : Dialog {

    private var dialogContext: DialogContext? = null
    private val prompts: MutableList<Prompt> = ArrayList()

    fun addPrompt(prompt: Prompt) {
        prompts.add(prompt)
    }

    fun addPrompt(name: String, question: String) {
        addPrompt(Prompt(name, question))
    }

    fun nextPrompt(): Prompt? = prompts.firstOrNull { prompt -> prompt.answer == null }

    override fun getDialogContext(): DialogContext? {
        prompts.forEach { prompt -> dialogContext?.dialogData?.put(prompt.name, prompt) }
        return dialogContext
    }

    override fun restoreFromContext(context: DialogContext) {
        this.dialogContext = context

        if (context.dialogData.isNotEmpty()) {
            this.prompts.clear()
            context.dialogData.values
                .map { obj -> obj as Prompt }
                .forEach { this.prompts.add(it) }
        }
    }

    override fun getAnswers(): Map<String, String?> = prompts.associate { it.name to it.answer }
}