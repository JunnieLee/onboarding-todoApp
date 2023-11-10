package com.example.todoapp.di

import com.example.data.source.dao.ContentDao
import com.example.domain.repository.ContentRepository
import com.example.data.repository.ContentRepositoryImpl
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
    fun providesContentRepository(contentDao: com.example.data.source.dao.ContentDao): com.example.domain.repository.ContentRepository =
        com.example.data.repository.ContentRepositoryImpl(contentDao)

}