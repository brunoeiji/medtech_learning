package com.example.medtechchat.features.chat_rest.data.repository

import com.example.medtechchat.components.chatview.MessageEntity
import com.example.medtechchat.features.chat_rest.data.ChatRestApiService
import com.example.medtechchat.features.chat_rest.data.models.request.SendNewMessageRequest
import com.example.medtechchat.features.chat_rest.domain.repository.IChatRestRepository
import com.example.medtechchat.network.ResponseWrapper
import com.example.medtechchat.network.doRequest
import javax.inject.Inject

class ChatRestRepositoryImpl @Inject constructor(
    private val apiService: ChatRestApiService
): IChatRestRepository {
    override suspend fun getMessages(): ResponseWrapper<Array<MessageEntity>> {
        return when(val request = doRequest { apiService.getMessages() }){
            is ResponseWrapper.Success -> {
                ResponseWrapper.Success(MessageEntity.mapper(request.result!!))
            }
            is ResponseWrapper.Error -> {
                ResponseWrapper.Error(request.message ?: "")
            }
        }
    }

    override suspend fun sendNewMessage(
        message: String,
        authorName: String,
        authorImage: String,
        isImage: Boolean
    ): ResponseWrapper<Unit> {
        val bodyRequest = SendNewMessageRequest(
            message = message,
            author = authorName,
            authorAvatar = authorImage,
            isImage = isImage
        )
        return when(doRequest { apiService.sendNewMessage(bodyRequest) }){
            is ResponseWrapper.Success -> {
                ResponseWrapper.Success(Unit)
            }
            is ResponseWrapper.Error -> {
                ResponseWrapper.Error("")
            }
        }
    }
}