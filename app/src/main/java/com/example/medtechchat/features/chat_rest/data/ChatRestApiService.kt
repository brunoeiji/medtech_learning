package com.example.medtechchat.features.chat_rest.data

import com.example.medtechchat.features.chat_rest.data.models.request.SendNewMessageRequest
import com.example.medtechchat.features.chat_rest.data.models.response.GetMessageResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface ChatRestApiService {

    @GET("/api/rest/chat")
    suspend fun getMessages(): Response<GetMessageResponse>

    @POST("/api/rest/chat/send")
    suspend fun sendNewMessage(
        @Body request: SendNewMessageRequest
    ): Response<Unit>

}