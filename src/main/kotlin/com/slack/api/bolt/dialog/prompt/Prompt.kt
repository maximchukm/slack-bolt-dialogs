package com.slack.api.bolt.dialog.prompt

import java.io.Serializable

data class Prompt(
    val name: String,
    val question: String,
    var answer: String? = null,
) : Serializable
