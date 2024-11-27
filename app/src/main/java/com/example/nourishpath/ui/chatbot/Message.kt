package com.example.nourishpath.ui.chatbot

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class Message(
    val text: String,
    val question: String,
    val questions: List<Question>
)