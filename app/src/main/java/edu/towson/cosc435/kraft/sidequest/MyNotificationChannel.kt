package edu.towson.cosc435.kraft.sidequest

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

class MyNotificationChannel: Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                QuestCounterNotificationService.QUEST_COUNTER_CHANNEL_ID,
                "quest counter",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            channel.description = "Used to count pending quests"
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}