package com.slack.api.bolt.dialog

import com.slack.api.bolt.dialog.context.DialogContext

interface Dialog {

    fun getDialogContext(): DialogContext?

    fun restoreFromContext(context: DialogContext)

    fun getAnswers(): Map<String, String?>

}