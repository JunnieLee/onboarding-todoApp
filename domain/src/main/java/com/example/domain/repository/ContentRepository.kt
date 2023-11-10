package com.example.domain.repository

import com.example.domain.model.TextContent

interface ContentRepository {
    suspend fun insert(item: TextContent)
    suspend fun modify(item: TextContent)
    suspend fun delete(item: TextContent)
    suspend fun deleteMultiple(items: List<TextContent>)
    suspend fun read(id: Int): TextContent?
    suspend fun loadList(): List<TextContent>
}