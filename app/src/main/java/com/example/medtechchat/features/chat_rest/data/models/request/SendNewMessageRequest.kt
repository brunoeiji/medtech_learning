package com.example.medtechchat.features.chat_rest.data.models.request

data class SendNewMessageRequest(
    val message: String,
    val author: String,
    val authorAvatar: String,
    val isImage: Boolean
)