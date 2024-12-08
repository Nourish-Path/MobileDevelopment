package com.example.nourishpath.ui.reminder.worker

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.nourishpath.ui.reminder.helper.NotificationHelper

class VitaminWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {
    override fun doWork(): Result {
        Log.d("VitaminWorker", "Vitamin reminder executed")
        val notification = NotificationHelper.getVitaminNotification(
            applicationContext,
            "It's time to consumes vitamin!",
            "Don't forget to consumes vitamin today!"
        ).build()

        val notificationManager = ContextCompat.getSystemService(applicationContext, NotificationManager::class.java)
        notificationManager?.notify(1, notification)

        return Result.success()
    }
}
