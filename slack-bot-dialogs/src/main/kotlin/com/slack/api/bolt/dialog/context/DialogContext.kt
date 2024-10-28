package com.slack.api.bolt.dialog.context

import com.slack.api.bolt.dialog.Dialog
import java.io.Serializable

data class DialogContext(
    val dialogClass: Class<out Dialog>,
    val userId: String,
    val dialogData: MutableMap<String, Serializable> = HashMap()
) : Serializable
