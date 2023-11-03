package com.example.todoapp.data.di

import com.example.todoapp.data.dao.ContentDao
import com.example.todoapp.repository.ContentRepository
import com.example.todoapp.repository.ContentRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun providesContentRepository(contentDao: ContentDao): ContentRepository =
        ContentRepositoryImpl(contentDao)

}