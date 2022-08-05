package com.example.medtechchat.features.chat_rest.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.medtechchat.components.chatview.ChatView
import com.example.medtechchat.components.chatview.IChatView
import com.example.medtechchat.components.chatview.MessageEntity
import com.example.medtechchat.databinding.FragmentChatRestBinding
import com.example.medtechchat.features.chat_rest.data.models.response.Message
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatRestFragment : Fragment(), IChatView {

    private val viewModel: ChatRestViewModel by viewModels()
    private var _binding: FragmentChatRestBinding? = null
    private val binding get() = _binding!!

    private lateinit var chatView: ChatView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatRestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        fetchData()
        setupObservables()
        setupSocketIO()
    }

    private fun fetchData() {
        viewModel.getMessages()
    }

    private fun setupView() {
        chatView = ChatView(binding.graphqlChat, this, this)
    }

    private fun setupSocketIO() {
        val socket = viewModel.getSocket()
        socket.on("newMessage") { args ->
            args[0]?.let { json ->
                val message = Gson().fromJson(json.toString(), Message::class.java)
                activity?.runOnUiThread {
                    chatView.addNewMessage(MessageEntity.mapper(message))
                }
            }
        }
    }

    private fun setupObservables() {
        viewModel.messagesLiveData.observe(viewLifecycleOwner) {
            chatView.messagesArray = it
            chatView.authorId = "eu"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onNewMessage(message: MessageEntity) {
        viewModel.sendNewMessage(message)
    }
}