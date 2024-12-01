package com.example.nourishpath.ui.home.helper

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.nourishpath.R

object NotificationHelper {
    const val CHANNEL_ID_VITAMIN = "channel_vitamin"
    const val CHANNEL_ID_DRINK = "channel_drink"

    fun createNotificationChannels(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val vitaminChannel = NotificationChannel(
                CHANNEL_ID_VITAMIN,
                "Vitamin Reminders",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifikasi untuk mengonsumsi vitamin"
            }

            val drinkChannel = NotificationChannel(
                CHANNEL_ID_DRINK,
                "Drink Water Reminders",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifikasi untuk minum air putih"
            }

            val manager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(vitaminChannel)
            manager.createNotificationChannel(drinkChannel)
        }
    }
    fun getVitaminNotification(context: Context, title: String, message: String): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, CHANNEL_ID_VITAMIN)
            .setSmallIcon(R.drawable.ic_vitamins)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
    }

    fun getDrinkNotification(context: Context, title: String, message: String): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, CHANNEL_ID_DRINK)
            .setSmallIcon(R.drawable.ic_water)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
    }
}