package com.dudko.shoppinglistnotificator

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class ShoppingListReceiver : BroadcastReceiver() {
    private lateinit var service: NotificationService

    fun setService (service: NotificationService) {
        this.service = service
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null) {
            service.sendNotification(intent)
        }
    }
}