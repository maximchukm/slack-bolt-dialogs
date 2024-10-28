package com.slack.api.bolt.dialog.context

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class DefaultDialogContextService : DialogContextService {

    private val contextMap: ConcurrentMap<String, DialogContext> = ConcurrentHashMap()

    override fun saveContext(dialogContext: DialogContext) {
        contextMap[dialogContext.userId] = dialogContext
    }

    override fun restoreContext(userId: String) = contextMap[userId]

}