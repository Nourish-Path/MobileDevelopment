package com.example.nourishpath.ui.home

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.work.*
import com.example.nourishpath.databinding.FragmentHomeBinding
import com.example.nourishpath.ui.home.helper.NotificationHelper
import com.example.nourishpath.ui.home.worker.DrinkWorker
import com.example.nourishpath.ui.home.worker.VitaminWorker
import com.example.nourishpath.ui.profile.ProfileActivity
import java.util.concurrent.TimeUnit

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
        NotificationHelper.createNotificationChannels(requireContext())

        // Mengatur tema
        sharedPreferences =
            requireContext().getSharedPreferences("user_preferences", Context.MODE_PRIVATE)
        val isDarkMode = sharedPreferences.getBoolean("DARK_MODE", false)
        binding.themeSwitch.isChecked = isDarkMode
        setDarkMode(isDarkMode)

        // Event saat profile picture diklik
        binding.ivProfilePicture.setOnClickListener {
            val intent = Intent(requireContext(), ProfileActivity::class.java)
            startActivity(intent)
        }

        // Menampilkan nama pengguna dari SharedPreferences
        val userName = sharedPreferences.getString("user_name", "Guest")
        binding.tvUserName.text = userName

        // Toggle untuk dark mode
        binding.themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked != isDarkMode) {
                setDarkMode(isChecked)
                with(sharedPreferences.edit()) {
                    putBoolean("DARK_MODE", isChecked)
                    apply()
                }
            }
        }

    // Menjadwalkan pengingat vitamin (setiap 24 jam)
    val vitaminRequest = PeriodicWorkRequestBuilder<VitaminWorker>(24, TimeUnit.HOURS)
        .build()
    WorkManager.getInstance(requireContext())
    .enqueueUniquePeriodicWork("VitaminReminder", ExistingPeriodicWorkPolicy.REPLACE, vitaminRequest)

    // Menjadwalkan pengingat minum air putih (setiap 2 jam)
    val drinkRequest = PeriodicWorkRequestBuilder<DrinkWorker>(2, TimeUnit.HOURS)
        .build()
    WorkManager.getInstance(requireContext())
    .enqueueUniquePeriodicWork("DrinkReminder", ExistingPeriodicWorkPolicy.REPLACE, drinkRequest)
    }

private fun setDarkMode(isDarkMode: Boolean) {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}