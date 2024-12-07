package com.example.nourishpath.ui.chatbot

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nourishpath.R
import com.example.nourishpath.databinding.ActivityChatbotBinding

class ChatbotActivity : AppCompatActivity(), QuestionAdapter.OnItemClickedCallback {
    private lateinit var binding: ActivityChatbotBinding
    private lateinit var chatbotAdapter: ChatbotAdapter
    private val listMessages = ArrayList<Message>()

    private lateinit var listQuestions: List<String>
    private lateinit var listAnswers: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatbotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listAnswers = resources.getStringArray(R.array.data_answer).toList()

        val questionsArray = resources.getStringArray(R.array.data_question)
        val questions = questionsArray.map { Question(it) }

        val firstMessage = Message(
            text = "Welcome! Please pick your question.",
            question = "",
            questions = questions
        )

        chatbotAdapter = ChatbotAdapter(listMessages, this) // Pass callback
        binding.rvChatHistory.layoutManager = LinearLayoutManager(this)
        binding.rvChatHistory.adapter = chatbotAdapter

        listMessages.add(firstMessage)
    }

    private fun getListQuestion(): ArrayList<Question> {
        val questions = resources.getStringArray(R.array.data_question)

        val questionList = ArrayList<Question>()

        for (i in questions.indices) {
            val question = Question(questions[i])
            questionList.add(question)
        }
        return questionList
    }

    override fun onItemClicked(question: Question) {
        Log.d("onItemClicked", "Sudah di klik")
        // Cari indeks dari pertanyaan yang dipilih
        val index = resources.getStringArray(R.array.data_question).indexOf(question.text)
        val questionsArray = getListQuestion()

        // Validasi indeks
        if (index != -1) {
            Log.d("index != -1", "If dalam onItemClicked dikerjakan")

            val answer = listAnswers[index]

            val replyMessage = Message(
                text = answer,
                question = question.text,
                questions = questionsArray
            )

            Log.d("ListMSG ADD", "Item list ditambahkan")
            listMessages.add(replyMessage)
            chatbotAdapter.notifyDataSetChanged() // Notifikasi bahwa data telah berubah

            binding.rvChatHistory.smoothScrollToPosition(listMessages.size - 1)
            Toast.makeText(this, "${question.text}", Toast.LENGTH_SHORT).show()
        } else {
            // Handle jika pertanyaan tidak ditemukan (opsional)
            Toast.makeText(this, "Pertanyaan tidak ditemukan.", Toast.LENGTH_SHORT).show()
        }
    }
}