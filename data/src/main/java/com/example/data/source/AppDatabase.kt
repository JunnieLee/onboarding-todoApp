package com.example.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.source.dao.ContentDao
import com.example.data.model.ContentEntity

@Database(entities=[ContentEntity::class], version=1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contentDao(): ContentDao
}