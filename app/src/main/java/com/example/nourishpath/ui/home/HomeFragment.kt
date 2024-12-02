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
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.nourishpath.ui.nutrient.ChildInputActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.nourishpath.R
import com.example.nourishpath.databinding.FragmentHomeBinding
import com.example.nourishpath.ui.reminder.helper.NotificationHelper
import com.example.nourishpath.ui.chatbot.ChatbotActivity
import com.example.nourishpath.ui.profile.ProfileActivity
import com.example.nourishpath.ui.profile.ProfileViewModel
import com.example.nourishpath.ui.reminder.NotificationActivity
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var homeViewModel: HomeViewModel

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

        homeViewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[HomeViewModel::class.java]

        // Event saat Remind Me! diklik
        binding.remindMe.setOnClickListener {
            val intent = Intent(requireContext(), NotificationActivity::class.java)
            startActivity(intent)
        }

        loadProfilePicture()

        // Event saat profile picture diklik
        binding.ivProfilePicture.setOnClickListener {
            val intent = Intent(requireContext(), ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.askDrBot.setOnClickListener {
            val intent = Intent(requireContext(),ChatbotActivity::class.java)
            startActivity(intent)
        }
        homeViewModel.fetchProfile()

        homeViewModel.profile.observe(viewLifecycleOwner) { profile ->
            profile?.let {
                binding.tvUserName.text = it.name
                Log.d("HomeFragment", "User Name: ${it.name}")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.fetchProfile()
        loadProfilePicture()
    }
    private fun loadProfilePicture() {
        val sharedPref = requireContext().getSharedPreferences("ProfilePrefs", Context.MODE_PRIVATE)
        val profileUri = sharedPref.getString("profileImageUri", null)
        if (profileUri != null) {
            val uri = Uri.parse(profileUri)
            binding.ivProfilePicture.setImageURI(uri)
            Log.d("HomeFragment", "Loaded profile image URI: $profileUri")
        } else {
            Log.d("HomeFragment", "No profile image URI found.")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}