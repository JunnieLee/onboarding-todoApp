package com.example.domain.usecase

import com.example.domain.model.TextContent
import com.example.domain.repository.ContentRepository
import javax.inject.Inject

class GetToDoUseCase @Inject constructor(
    private val contentRepository: ContentRepository
) {
    suspend fun invoke(id: Int): TextContent? {
        return contentRepository.read(id)
    }
}