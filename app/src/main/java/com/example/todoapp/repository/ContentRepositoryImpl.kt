package com.example.todoapp.repository

import com.example.todoapp.data.dao.ContentDao
import com.example.todoapp.model.ContentEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ContentRepositoryImpl @Inject constructor(private val contentDao: ContentDao):ContentRepository {

    override fun loadList() = contentDao.selectAll()

    override suspend fun insert(item: ContentEntity) {
        contentDao.insert(item)
    }

    override suspend fun modify(item: ContentEntity) {
        contentDao.insert(item) // 어짜피 충돌 발생시 덮어씌우기 때문에..
    }
}