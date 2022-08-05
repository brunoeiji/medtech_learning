package com.example.medtechchat.components.chatview

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.medtechchat.databinding.ChatViewBinding
import java.io.ByteArrayOutputStream
import java.util.*


class ChatView(private val binding: ChatViewBinding, val fragment: Fragment, val delegate: IChatView) {

    var authorId: String = ""
        set(value) {
            adapter.setAuthorId(value)
            field = value
        }
    var messagesArray = arrayListOf<MessageEntity>()
        set(value) {
            adapter.setupMessages(ArrayList(value.reversed()))
            field = ArrayList(value.reversed())
        }
    var adapter: ChatAdapter = ChatAdapter(messagesArray, authorId)
    var imagePickerResultActivityLauncher: ActivityResultLauncher<String>

    init {
        binding.messagesRecyclerView.adapter = adapter
        imagePickerResultActivityLauncher =
            fragment.registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
                result?.let { uri ->
                    val imageStream = fragment.context?.contentResolver?.openInputStream(uri);
                    val selectedImage = BitmapFactory.decodeStream(imageStream);
                    val msg = encodeImage(selectedImage) ?: ""
                    val newMessageEntity = createNewMessage(msg, true)
                    delegate.onNewMessage(newMessageEntity)
                }
            }
        setupButtons()
    }

    fun addNewMessage(message: MessageEntity){
        adapter.newMessage(message)
    }

    private fun setupButtons() {
        binding.apply {
            sendButton.setOnClickListener {
                val msg = messageInput.editText?.text.toString()
                val newMessageEntity = createNewMessage(msg, false)
                messageInput.editText?.setText("")
                delegate.onNewMessage(newMessageEntity)
            }

            galleryButton.setOnClickListener {
                imagePickerResultActivityLauncher.launch("image/*")
            }

        }
    }

    private fun createNewMessage(message: String, isImage: Boolean) =
        MessageEntity(
            message = message,
            authorId = authorId,
            authorName = authorId,
            authorImage = "",
            createdAt = "",
            isImage = isImage
        )

    private fun encodeImage(bm: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 20, baos)
        val b: ByteArray = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }
}

interface IChatView{
    fun onNewMessage(message: MessageEntity)
}

