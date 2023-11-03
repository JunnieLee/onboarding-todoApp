package com.example.todoapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todoapp.data.dao.ContentDao
import com.example.todoapp.model.ContentEntity

@Database(entities=[ContentEntity::class], version=1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contentDao(): ContentDao
}