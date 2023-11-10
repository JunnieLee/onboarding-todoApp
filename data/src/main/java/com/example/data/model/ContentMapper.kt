package com.example.data.model

import com.example.domain.model.TextContent

object ContentMapper {
    fun TextContent.toContentEntity() = ContentEntity(
        id= id,
        content =content,
        memo=memo,
        isDone=isDone,
    )

    fun ContentEntity.toContent() = TextContent(
        id= id,
        content =content,
        memo=memo,
        isDone=isDone,
    )

    fun ContentEntity.toTextContent() = TextContent(
        id= id,
        content =content,
        memo=memo,
        isDone=isDone,
    )

}