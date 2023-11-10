package com.example.todoapp.di

import com.example.data.source.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Singleton
    @Provides
    fun providesContentDao(appDatabase: com.example.data.source.AppDatabase) = appDatabase.contentDao()
}