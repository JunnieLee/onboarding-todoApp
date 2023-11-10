package com.example.todoapp.di

import android.content.Context
import androidx.room.Room
import com.example.data.source.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesDataBase(@ApplicationContext context: Context): com.example.data.source.AppDatabase {
        return Room.databaseBuilder(context, com.example.data.source.AppDatabase::class.java, "todoApp.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}