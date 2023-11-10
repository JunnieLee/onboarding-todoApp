package com.example.domain.usecase
import com.example.domain.model.TextContent
import com.example.domain.repository.ContentRepository
import javax.inject.Inject

class ModifyToDoUseCase @Inject constructor(
    private val contentRepository: ContentRepository
)  {
    suspend fun invoke(item: TextContent) {
        contentRepository.modify(item)
    }
}

