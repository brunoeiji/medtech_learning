package com.example.medtechchat.features.chat_rest.data.models.response

data class Message(
    val author: String,
    val authorAvatar: String,
    val isImage: Boolean,
    val createdAt: String,
    val message: String
)