package com.example.medtechchat.features.chat_graphql.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.medtechchat.components.chatview.ChatView
import com.example.medtechchat.components.chatview.IChatView
import com.example.medtechchat.components.chatview.MessageEntity
import com.example.medtechchat.databinding.FragmentChatGraphqlBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatGraphQLFragment : Fragment(), IChatView {

    private val viewModel: ChatGraphQLViewModel by viewModels()
    private var _binding: FragmentChatGraphqlBinding? = null
    private val binding get() = _binding!!

    private lateinit var chatView: ChatView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatGraphqlBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        fetchData()
        setupObservables()
    }

    private fun fetchData() {
        viewModel.getMessages()
    }

    private fun setupView() {
        chatView = ChatView(binding.graphqlChat, this, this)
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