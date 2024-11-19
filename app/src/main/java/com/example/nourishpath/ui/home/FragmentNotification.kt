package com.example.nourishpath.ui.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.nourishpath.R
import com.example.nourishpath.databinding.FragmentNotificationsBinding
import com.example.nourishpath.ui.home.worker.DrinkWorker
import com.example.nourishpath.ui.home.worker.VitaminWorker
import java.util.concurrent.TimeUnit

class FragmentNotification : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // SharedPreferences untuk menyimpan status switch
        sharedPreferences = requireContext().getSharedPreferences("user_preferences", Context.MODE_PRIVATE)

        // Set status awal dari Switch
        binding.switchVitaminReminder.isChecked = sharedPreferences.getBoolean("vitamin_reminder_enabled", false)
        binding.switchDrinkReminder.isChecked = sharedPreferences.getBoolean("drink_reminder_enabled", false)

        // Event untuk mengaktifkan pengingat vitamin
        binding.switchVitaminReminder.setOnCheckedChangeListener { _, isChecked ->
            with(sharedPreferences.edit()) {
                putBoolean("vitamin_reminder_enabled", isChecked)
                apply()
            }
            if (isChecked) {
                val vitaminRequest = PeriodicWorkRequestBuilder<VitaminWorker>(24, TimeUnit.HOURS).build()
                WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork(
                    "VitaminReminder", ExistingPeriodicWorkPolicy.REPLACE, vitaminRequest
                )
            } else {
                WorkManager.getInstance(requireContext()).cancelUniqueWork("VitaminReminder")
            }
        }

        // Event untuk mengaktifkan pengingat minum air
        binding.switchDrinkReminder.setOnCheckedChangeListener { _, isChecked ->
            with(sharedPreferences.edit()) {
                putBoolean("drink_reminder_enabled", isChecked)
                apply()
            }
            if (isChecked) {
                val drinkRequest = PeriodicWorkRequestBuilder<DrinkWorker>(2, TimeUnit.HOURS).build()
                WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork(
                    "DrinkReminder", ExistingPeriodicWorkPolicy.REPLACE, drinkRequest
                )
            } else {
                WorkManager.getInstance(requireContext()).cancelUniqueWork("DrinkReminder")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
