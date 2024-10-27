package com.slack.api.bolt.dialog.context

interface DialogContextService {

    fun saveContext(dialogContext: DialogContext)

    fun restoreContext(userId: String): DialogContext?

}