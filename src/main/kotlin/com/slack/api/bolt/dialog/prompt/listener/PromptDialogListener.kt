package com.slack.api.bolt.dialog.prompt.listener

import com.slack.api.bolt.context.builtin.EventContext
import com.slack.api.bolt.dialog.listener.DialogListener
import com.slack.api.bolt.dialog.prompt.Prompt
import com.slack.api.bolt.dialog.prompt.PromptDialog
import com.slack.api.methods.SlackApiException
import java.io.IOException

interface PromptDialogListener: DialogListener {

    @Throws(IOException::class, SlackApiException::class)
    fun onPromptCompleted(dialog: PromptDialog, prompt: Prompt, context: EventContext)

}