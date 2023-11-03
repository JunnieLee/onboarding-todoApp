package com.example.todoapp.repository

import com.example.todoapp.model.ContentEntity
import kotlinx.coroutines.flow.Flow

interface ContentRepository {

    fun loadList(): Flow<List<ContentEntity>>
    suspend fun insert(item:ContentEntity)

}