package com.example.domain.usecase

import com.example.domain.model.Content
import com.example.domain.model.TextContent
import com.example.domain.repository.ContentRepository
import javax.inject.Inject

class LoadToDoListUseCase @Inject constructor(
    private val contentRepository: ContentRepository
) {
    suspend fun invoke(): List<TextContent> {
        return contentRepository.loadList()
    }
}