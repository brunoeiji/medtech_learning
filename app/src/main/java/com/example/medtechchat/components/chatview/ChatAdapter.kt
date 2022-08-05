package com.example.medtechchat.components.chatview

import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.allViews
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.medtechchat.R
import kotlin.collections.ArrayList

class ChatAdapter(private var messages: ArrayList<MessageEntity>, private var authorId: String) :
    RecyclerView.Adapter<ChatAdapter.MessageViewHolder>() {

    private lateinit var recyclerView: RecyclerView

    fun setupMessages(messages: ArrayList<MessageEntity>){
        this.messages = messages
        notifyDataSetChanged()
        recyclerView.smoothScrollToPosition(this.messages.size)
    }

    fun newMessage(messageEntity: MessageEntity){
        this.messages.add(messageEntity)
        notifyItemInserted(this.messages.count())
        recyclerView.smoothScrollToPosition(this.messages.size)
    }

    fun setAuthorId(authorId: String){
        this.authorId = authorId
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun getItemViewType(position: Int): Int {
        // 0 for author text
        // 1 for author image
        // 2 for others text
        // 3 for others image
        val message = messages[position]
        var type = if (message.authorId == authorId) {
            0
        } else {
            2
        }
        if (message.isImage) {
            type++
        }
        return type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return when(viewType){
            0 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.message_author, parent, false)
                TextMessageViewHolder(view)
            }
            1 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.message_author, parent, false)
                ImageMessageViewHolder(view)
            }
            2 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.message_other, parent, false)
                TextMessageViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.message_other, parent, false)
                ImageMessageViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        val isAuthor = position < 2
        holder.bind(message, isAuthor = isAuthor)
    }

    override fun getItemCount() = messages.size

    class ImageMessageViewHolder(itemView: View): MessageViewHolder(itemView){
        override fun bind(entity: MessageEntity, isAuthor: Boolean){
            val imageSize = 400
            val imageView = ImageView(itemView.context)
            imageView.layoutParams = LinearLayout.LayoutParams(imageSize, imageSize)
            val byteArray = Base64.decode(entity.message, Base64.DEFAULT)

            Glide.with(itemView.context)
                .load(byteArray)
//                .load(entity.message)
                .fitCenter()
                .into(imageView)

            super.bind(entity.createdAt, imageView, entity.authorImage, entity.authorName)
        }
    }

    class TextMessageViewHolder(itemView: View): MessageViewHolder(itemView){
        override fun bind(entity: MessageEntity, isAuthor: Boolean){
            val textView = TextView(itemView.context)
            textView.text = entity.message
            super.bind(entity.createdAt, textView, entity.authorImage, entity.authorName)
        }
    }

    abstract class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val createdAtTextView: TextView = itemView.findViewById(R.id.message_created_text)
        private val container: LinearLayout = itemView.findViewById(R.id.message_content_container)
        private val avatarImageView = itemView.findViewById<ImageView>(R.id.profile_image)
        private val authorName = itemView.findViewById<TextView>(R.id.author_name_text)

        fun bind(createdAt: String, content: View, avatarUrl: String, name: String){
            createdAtTextView.text = createdAt
            container.removeAllViews()
            container.addView(content)
            avatarImageView?.let {
                Glide.with(itemView.context)
                    .load(avatarUrl)
                    .circleCrop()
                    .into(it)
            }
            authorName?.let {
                it.text = name
            }
        }

        abstract fun bind(entity: MessageEntity, isAuthor: Boolean)
    }
}