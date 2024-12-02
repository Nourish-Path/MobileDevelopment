package com.example.nourishpath.ui.reminder

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.nourishpath.databinding.ActivityNotificationBinding
import com.example.nourishpath.ui.reminder.worker.DrinkWorker
import com.example.nourishpath.ui.reminder.worker.VitaminWorker
import java.util.concurrent.TimeUnit

class NotificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE)

        binding.switchVitaminReminder.isChecked = sharedPreferences.getBoolean("vitamin_reminder_enabled", false)
        binding.switchDrinkReminder.isChecked = sharedPreferences.getBoolean("drink_reminder_enabled", false)

        binding.switchVitaminReminder.setOnCheckedChangeListener { _, isChecked ->
            with(sharedPreferences.edit()) {
                putBoolean("vitamin_reminder_enabled", isChecked)
                apply()
            }
            if (isChecked) {
                // Pengingat vitamin setiap 24 jam
                val vitaminRequest = PeriodicWorkRequestBuilder<VitaminWorker>(24, TimeUnit.HOURS)
                    .build()
                WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                    "VitaminReminder", ExistingPeriodicWorkPolicy.REPLACE, vitaminRequest
                )
            } else {
                WorkManager.getInstance(this).cancelUniqueWork("VitaminReminder")
            }
        }

        binding.switchDrinkReminder.setOnCheckedChangeListener { _, isChecked ->
            with(sharedPreferences.edit()) {
                putBoolean("drink_reminder_enabled", isChecked)
                apply()
            }
            if (isChecked) {
                // Pengingat minum air setiap 2 jam
                val drinkRequest = PeriodicWorkRequestBuilder<DrinkWorker>(2, TimeUnit.HOURS)
                    .build()
                WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                    "DrinkReminder", ExistingPeriodicWorkPolicy.REPLACE, drinkRequest
                )
            } else {
                WorkManager.getInstance(this).cancelUniqueWork("DrinkReminder")
            }
        }

        binding.imageView.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}