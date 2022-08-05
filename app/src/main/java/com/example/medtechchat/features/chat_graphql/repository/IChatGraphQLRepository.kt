package com.example.medtechchat.features.chat_graphql.repository

import com.example.medtechchat.components.chatview.MessageEntity
import com.example.medtechchat.network.ResponseWrapper

interface IChatGraphQLRepository {

    suspend fun getMessages(): ResponseWrapper<Array<MessageEntity>>

    suspend fun sendNewMessage(
        message: String,
        authorName: String,
        authorImage: String,
        isImage: Boolean
    ): ResponseWrapper<Unit>

}