package com.slack.api.bolt.dialog.listener

import com.slack.api.bolt.context.builtin.EventContext
import com.slack.api.bolt.dialog.Dialog
import com.slack.api.methods.SlackApiException
import java.io.IOException

interface DialogListener {

    val dialogClass: Class<out Dialog>

    @Throws(IOException::class, SlackApiException::class)
    fun onDialogCompleted(dialog: Dialog, eventContext: EventContext)

}