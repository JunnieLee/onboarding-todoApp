package com.example.data.repository

import com.example.data.source.dao.ContentDao
import com.example.data.model.ContentMapper.toContent
import com.example.data.model.ContentMapper.toContentEntity
import com.example.data.model.ContentMapper.toTextContent
import com.example.domain.model.TextContent
import com.example.domain.repository.ContentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ContentRepositoryImpl @Inject constructor(private val contentDao: ContentDao):
    ContentRepository {

    override suspend fun loadList(): List<TextContent> {
        return withContext(Dispatchers.IO) {
            return@withContext contentDao.selectAll().map { it.toContent() }
        }
    }

    override suspend fun insert(item: TextContent) {
        withContext(Dispatchers.IO) {
            contentDao.insert(item.toContentEntity())
        }
    }

    override suspend fun modify(item: TextContent) {
        withContext(Dispatchers.IO) {
            contentDao.insert(item.toContentEntity()) // 어짜피 충돌 발생시 덮어씌우기 때문에..
        }
    }

    override suspend fun delete(item: TextContent) {
        withContext(Dispatchers.IO) {
            contentDao.delete(item.toContentEntity())
        }
    }

    override suspend fun deleteMultiple(items: List<TextContent>) {
        withContext(Dispatchers.IO) {
            contentDao.deleteMultiple(items.map { it.toContentEntity() })
        }
    }

    override suspend fun read(id: Int): TextContent? {
        return withContext(Dispatchers.IO) {
            return@withContext contentDao.read(id)?.toTextContent()
        }
    }
}