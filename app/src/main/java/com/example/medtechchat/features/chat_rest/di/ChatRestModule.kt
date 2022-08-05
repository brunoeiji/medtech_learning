package com.example.medtechchat.features.chat_rest.di

import com.example.medtechchat.features.chat_rest.data.ChatRestApiService
import com.example.medtechchat.features.chat_rest.data.repository.ChatRestRepositoryImpl
import com.example.medtechchat.features.chat_rest.domain.repository.IChatRestRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ChatRestModule {
    @Provides
    @Singleton
    fun provideHomeService(retrofit: Retrofit): ChatRestApiService =
        retrofit.create(ChatRestApiService::class.java)

    @Provides
    @Singleton
    fun provideRepository(homeApiService: ChatRestApiService): IChatRestRepository =
        ChatRestRepositoryImpl(homeApiService)
}