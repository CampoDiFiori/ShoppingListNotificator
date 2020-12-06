package com.dudko.shoppinglistnotificator

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificationService : Service() {

    private var mBinder: NotificationServiceBinder = NotificationServiceBinder()
    private val requestCode = 1
    var id = 0

    inner class NotificationServiceBinder : Binder() {
        fun getService(): NotificationService = this@NotificationService
    }

    override fun onBind(intent: Intent): IBinder {
        createNotificationChannel()
        return mBinder
    }


    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                    getString(R.string.channelID),
                    getString(R.string.channel_name),
                    NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationChannel.description = getString(R.string.channel_description)
            val notificationManager = NotificationManagerCompat.from(this)
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    fun sendNotification(broadcastIntent: Intent) {

        val pendingIntent = broadcastIntent.getParcelableExtra<PendingIntent>("edit_item_activity")
        val notificationMessage = broadcastIntent.getStringExtra("notification_message")

        val notification = NotificationCompat.Builder(this, getString(R.string.channelID))
                .setContentTitle("New product added")
                .setContentText(notificationMessage)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .build()

        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(id++, notification)
    }
}