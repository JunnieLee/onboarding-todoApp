package com.example.todoapp.repository

import com.example.todoapp.data.dao.ContentDao
import com.example.todoapp.model.ContentEntity
import javax.inject.Inject

class ContentRepositoryImpl @Inject constructor(private val contentDao: ContentDao):ContentRepository {
    override suspend fun insert(item: ContentEntity) {
        contentDao.insert(item)
    }
}