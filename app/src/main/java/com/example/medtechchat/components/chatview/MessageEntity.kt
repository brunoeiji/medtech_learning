package com.example.medtechchat.components.chatview

import com.example.medtechchat.features.chat_rest.data.models.response.GetMessageResponse
import com.example.medtechchat.features.chat_rest.data.models.response.Message

class MessageEntity(
    val message: String,
    val authorId: String,
    val authorName: String,
    val authorImage: String,
    val createdAt: String,
    val isImage: Boolean = false,
) {
    companion object {
        fun mapper(response: GetMessageResponse): Array<MessageEntity> {
            return response.messages.map {
                mapper(it)
            }.toTypedArray()
        }

        fun mapper(message: Message): MessageEntity {
            return MessageEntity(
                message = message.message,
                authorId = message.author,
                authorName = message.author,
                authorImage = message.authorAvatar,
                createdAt = message.createdAt,
                isImage = message.isImage
            )
        }
    }
}