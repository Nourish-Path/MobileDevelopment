package com.example.nourishpath.ui.home

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nourishpath.ui.childinput.ChildInputActivity
import androidx.lifecycle.ViewModelProvider
import com.example.nourishpath.databinding.FragmentHomeBinding
import com.example.nourishpath.ui.chatbot.ChatbotActivity
import com.example.nourishpath.ui.reminder.NotificationActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        binding.calculateNutrient.setOnClickListener {
            val intent = Intent(requireContext(), ChildInputActivity::class.java)
            startActivity(intent)
        }
        binding.remindMe.setOnClickListener {
            val intent = Intent(requireContext(), NotificationActivity::class.java)
            startActivity(intent)
        }

        binding.askDrBot.setOnClickListener {
            val intent = Intent(requireContext(),ChatbotActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}