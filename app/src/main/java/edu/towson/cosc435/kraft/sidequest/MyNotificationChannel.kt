package edu.towson.cosc435.kraft.sidequest

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

// my notification channel is used to set up the notification channel for the quest counter notification
class MyNotificationChannel: Application() {

    // overrides the on create function to set up the notification right when the application is launched
    override fun onCreate() {
        super.onCreate() // runs all the normal code for the onCreate function
        createNotificationChannel() // adds our function to on create to be executed after standard startup instructions are executed
    }

    // function used to create the notification channel
    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){ // checks if the proper build version is installed to create notification channel
            val channel = NotificationChannel(
                QuestCounterNotificationService.QUEST_COUNTER_CHANNEL_ID, // adds our quest counter notification service tothe channel
                "quest counter", // names the channel quest counter
                NotificationManager.IMPORTANCE_DEFAULT // sets the level of importance of the channel to the default level
            )

            channel.description = "Used to count pending quests" // sets the description of the channel to
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager // sets the notification manager using the context from the application
            notificationManager.createNotificationChannel(channel) // creates the notification channel
        }
    }
}