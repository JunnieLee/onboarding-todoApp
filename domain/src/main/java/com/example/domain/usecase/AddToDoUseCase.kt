package com.example.domain.usecase

import com.example.domain.model.TextContent
import com.example.domain.repository.ContentRepository
import javax.inject.Inject

class AddToDoUseCase @Inject constructor(
    private val contentRepository: ContentRepository
)  {
    suspend fun invoke(content: String, memo: String): TextContent {
        val data = TextContent(content=content,memo=memo)
        contentRepository.insert(data)
        return data
    }
}