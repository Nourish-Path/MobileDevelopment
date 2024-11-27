package com.example.nourishpath.ui.chatbot

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nourishpath.databinding.QuestionsBinding

class QuestionAdapter(private val listQuestion: List<Question>): RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {
    private var onItemClickCallback: OnItemClickedCallback? = null

    interface OnItemClickedCallback {
        fun onItemClicked(question: Question)
    }

    fun setOnItemClickedCallback(callback: OnItemClickedCallback) {
        this.onItemClickCallback = callback
    }

    inner class QuestionViewHolder(private val binding: QuestionsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(question: Question) {
            binding.tvQuestion.text = question.text
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(question) // Memanggil callback saat item di-klik
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val binding = QuestionsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuestionViewHolder(binding)
    }

    override fun getItemCount(): Int = listQuestion.size

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question = listQuestion[position]
        holder.bind(question)
    }
}