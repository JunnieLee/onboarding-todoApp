package com.example.feature.main.model

import com.example.domain.model.Content
import com.example.domain.model.TextContent

data class TextContentUI(
    val id: Int = 0,
    val content: String,
    val memo: String? = null,
    val isDone: Boolean = false,
    val isChecked: Boolean = false,
)


// mappers as extension function

fun TextContent.toTextContentUI(isChecked: Boolean?=null) = TextContentUI(
    id = id,
    content = content,
    memo = memo,
    isDone = isDone,
    isChecked = isChecked?:false
)

fun TextContentUI.toTextContent() = TextContent(
    id = id,
    content = content,
    memo = memo,
    isDone = isDone,
)

fun List<TextContentUI>.filterIsChecked(): List<TextContentUI> {
    return this.filter { it.isChecked}
}

