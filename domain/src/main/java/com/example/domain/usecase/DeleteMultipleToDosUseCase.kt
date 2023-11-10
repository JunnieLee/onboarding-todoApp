package com.example.domain.usecase

import com.example.domain.model.TextContent
import com.example.domain.repository.ContentRepository
import javax.inject.Inject

class DeleteMultipleToDosUseCase @Inject constructor(
    private val contentRepository: ContentRepository
)  {
    suspend fun invoke(items: List<TextContent>) {
        contentRepository.deleteMultiple(items)
    }
}


