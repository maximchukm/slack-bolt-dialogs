package com.slack.api.bolt.dialog.context

import com.hazelcast.config.Config
import com.hazelcast.core.Hazelcast
import com.hazelcast.core.HazelcastInstance
import com.slack.api.bolt.dialog.context.hazelcast.DialogContextSerializer

class HazelcastDialogContextService(hazelcastConfig: Config ) : DialogContextService {

    private var hazelcastInstance: HazelcastInstance = hazelcastConfig
        .let {
            it.serializationConfig
                .compactSerializationConfig
                .addSerializer(DialogContextSerializer())
            Hazelcast.newHazelcastInstance(it)
        }

    override fun saveContext(dialogContext: DialogContext) {
        getMap(dialogContext.userId)[dialogContext.userId] = dialogContext
    }

    override fun restoreContext(userId: String): DialogContext? = getMap(userId)[userId]

    private fun getMap(userId: String): MutableMap<String, DialogContext> {
        val key = String.format("dialog_%s", userId)
        return hazelcastInstance.getMap(key)
    }

}