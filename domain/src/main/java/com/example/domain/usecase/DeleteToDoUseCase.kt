package com.example.domain.usecase
import com.example.domain.model.TextContent
import com.example.domain.repository.ContentRepository
import javax.inject.Inject

class DeleteToDoUseCase @Inject constructor(
    private val contentRepository: ContentRepository
)  {
    suspend fun invoke(item: TextContent) {
        contentRepository.delete(item)
    }
}


