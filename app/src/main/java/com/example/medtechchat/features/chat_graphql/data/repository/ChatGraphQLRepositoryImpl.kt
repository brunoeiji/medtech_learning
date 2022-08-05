package com.example.medtechchat.features.chat_graphql.data.repository

import com.example.medtechchat.components.chatview.MessageEntity
import com.example.medtechchat.features.chat_graphql.repository.IChatGraphQLRepository
import com.example.medtechchat.network.ResponseWrapper
import javax.inject.Inject

class ChatGraphQLRepositoryImpl @Inject constructor(
) : IChatGraphQLRepository {
    override suspend fun getMessages(): ResponseWrapper<Array<MessageEntity>> {
        return ResponseWrapper.Error("")
    }

    override suspend fun sendNewMessage(
        message: String,
        authorName: String,
        authorImage: String,
        isImage: Boolean
    ): ResponseWrapper<Unit> {
        return ResponseWrapper.Error("")
    }
}