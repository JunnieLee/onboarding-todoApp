package com.example.data.source.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.ContentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ContentDao {

    @Query("SELECT * FROM Content")
    fun selectAll(): List<ContentEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ContentEntity)

    @Delete
    suspend fun delete(item: ContentEntity)

    @Delete
    suspend fun deleteMultiple(items: List<ContentEntity>)

    @Query("SELECT * FROM Content WHERE id=:id")
    fun read(id:Int): ContentEntity
}