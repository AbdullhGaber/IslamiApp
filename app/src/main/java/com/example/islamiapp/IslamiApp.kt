package com.example.islamiapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationManagerCompat
import com.example.islamiapp.util.Constants.CHANNEL_ID

class IslamiApp : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }
    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManagerCompat.IMPORTANCE_DEFAULT
            val channel = NotificationChannelCompat.Builder(CHANNEL_ID, importance)
                .setName(name)
                .setDescription(descriptionText)
                .build()

            val notificationManager =  NotificationManagerCompat.from(this)
            notificationManager.createNotificationChannel(channel)
        }


    }
}