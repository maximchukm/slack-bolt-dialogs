package com.slack.api.bolt.dialog.context.hazelcast

import com.hazelcast.nio.serialization.HazelcastSerializationException
import com.hazelcast.nio.serialization.compact.CompactReader
import com.hazelcast.nio.serialization.compact.CompactSerializer
import com.hazelcast.nio.serialization.compact.CompactWriter
import com.slack.api.bolt.dialog.context.DialogContext
import java.io.*
import java.util.*

private const val DIALOG_CONTEXT_NAME = "dialogContext"
class DialogContextSerializer : CompactSerializer<DialogContext> {

    override fun read(compactReader: CompactReader): DialogContext {
        val inputStream = ByteArrayInputStream(
            nonNullable(compactReader.readArrayOfInt8(DIALOG_CONTEXT_NAME))
        )
        try {
            return ObjectInputStream(inputStream).readObject() as DialogContext
        } catch (e: Exception) {
            throw HazelcastSerializationException(e)
        }
    }

    override fun write(compactWriter: CompactWriter, context: DialogContext) {
        try {
            val byteArrayOutputStream = ByteArrayOutputStream()
            val objectOutputStream = ObjectOutputStream(byteArrayOutputStream)

            objectOutputStream.writeObject(context)

            compactWriter.writeArrayOfInt8(
                DIALOG_CONTEXT_NAME,
                byteArrayOutputStream.toByteArray()
            )
        } catch (e: IOException) {
            throw HazelcastSerializationException(e)
        }
    }

    override fun getTypeName(): String {
        return DialogContext::class.java.name
    }

    override fun getCompactClass(): Class<DialogContext> {
        return DialogContext::class.java
    }

    private fun <T> nonNullable(value: T): T {
        return Optional.ofNullable(value).orElseThrow {
            HazelcastSerializationException(
                "Cannot deserialize null value"
            )
        }
    }

}