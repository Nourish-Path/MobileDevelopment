package com.example.nourishpath.ui.reminder.worker

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.nourishpath.ui.reminder.helper.NotificationHelper

class DrinkWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {
    override fun doWork(): Result {
        Log.d("DrinkWorker", "Drink reminder executed")
        val notification = NotificationHelper.getDrinkNotification(
            applicationContext,
            "Waktunya Minum Air Putih",
            "Pastikan Anda minum 8 gelas air hari ini!"
        ).build()

        val notificationManager = ContextCompat.getSystemService(applicationContext, NotificationManager::class.java)
        notificationManager?.notify(2, notification)

        return Result.success()
    }
}
