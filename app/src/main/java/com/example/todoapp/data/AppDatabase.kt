package com.example.todoapp.data

import androidx.room.Database
import com.example.todoapp.data.dao.ContentDao
import com.example.todoapp.model.ContentEntity

@Database(entities=[ContentEntity::class], version=1)
abstract class AppDatabase {
    abstract fun contentDao(): ContentDao
}