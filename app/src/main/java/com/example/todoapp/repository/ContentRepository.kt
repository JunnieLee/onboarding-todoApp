package com.example.todoapp.repository

import com.example.todoapp.model.ContentEntity

interface ContentRepository {

    suspend fun insert(item:ContentEntity)

}