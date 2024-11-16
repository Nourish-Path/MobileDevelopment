package com.example.nourishpath.ui.chatbot

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nourishpath.R
import com.example.nourishpath.databinding.BotMessageBinding

class ChatbotAdapter(
    private val listMessage: ArrayList<Message>,
    private val onItemClickedCallback: QuestionAdapter.OnItemClickedCallback
): RecyclerView.Adapter<ChatbotAdapter.ChatViewHolder>()
{
    inner class ChatViewHolder(private val binding: BotMessageBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.ivBotProfile.setImageResource(R.drawable.ic_profile)
            binding.tvBotMessageText.text = message.text
            val questionAdapter = QuestionAdapter(message.questions)
            questionAdapter.setOnItemClickedCallback(onItemClickedCallback)
            binding.rvQuestionList.layoutManager = LinearLayoutManager(binding.root.context)
            binding.rvQuestionList.adapter = questionAdapter
        }
    }

    private fun addMessage(newMessage: Message) {
        listMessage.add(newMessage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = BotMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatViewHolder(binding)
    }

    override fun getItemCount(): Int = listMessage.size

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val message = listMessage[position]
        holder.bind(message)
    }
}