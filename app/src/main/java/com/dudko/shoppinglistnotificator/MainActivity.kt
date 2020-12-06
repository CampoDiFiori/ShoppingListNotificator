package com.dudko.shoppinglistnotificator

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.*
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {

    lateinit var receiver: ShoppingListReceiver
    private lateinit var mserv: NotificationService
    private var mBound: Boolean = false

    val mcom = object: ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as NotificationService.NotificationServiceBinder
            mserv = binder.getService()
            mBound = true

            receiver.setService(mserv)
        }
        override fun onServiceDisconnected(name: ComponentName?) {
            mBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        receiver = ShoppingListReceiver()
        val filter = IntentFilter("com.dudko.ITEM_ADDED")
        registerReceiver(receiver, filter)


        val serviceIntent = Intent(this, NotificationService::class.java)
        val lol = bindService(serviceIntent, mcom, Context.BIND_AUTO_CREATE)

        println(lol)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
        if (mBound) {
            unbindService(mcom)
            mBound = false
        }
    }
}