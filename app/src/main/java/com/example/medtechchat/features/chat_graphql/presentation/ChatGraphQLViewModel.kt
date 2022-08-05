package com.example.medtechchat.features.chat_graphql.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.medtechchat.components.chatview.MessageEntity
import com.example.medtechchat.features.chat_graphql.domain.repository.IChatGraphQLRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatGraphQLViewModel @Inject constructor(
    private val repository: IChatGraphQLRepository
) : ViewModel() {

    val messagesLiveData = MutableLiveData<ArrayList<MessageEntity>>()

    fun getMessages(){
//        viewModelScope.launch {
//            withContext(Dispatchers.IO){
//                val response = repository.getMessages()
//                response.result?.let {
//                    withContext(Dispatchers.Main){
//                        messagesLiveData.value = ArrayList(it.toList())
//                    }
//                }
//            }
//        }
    }

    fun sendNewMessage(message: MessageEntity){
//        viewModelScope.launch {
//            withContext(Dispatchers.IO){
//                val response = repository.sendNewMessage(
//                    message = message.message,
//                    authorImage = message.authorImage,
//                    authorName = message.authorName,
//                    isImage = message.isImage
//                )
//            }
//        }
    }

}