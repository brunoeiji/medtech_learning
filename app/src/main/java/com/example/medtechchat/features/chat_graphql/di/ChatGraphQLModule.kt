package com.example.medtechchat.features.chat_graphql.di

import com.example.medtechchat.features.chat_graphql.data.repository.ChatGraphQLRepositoryImpl
import com.example.medtechchat.features.chat_graphql.repository.IChatGraphQLRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ChatGraphQLModule {
    @Provides
    @Singleton
    fun provideRepository(): IChatGraphQLRepository =
        ChatGraphQLRepositoryImpl()
}