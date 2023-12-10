package edu.towson.cosc435.kraft.sidequest

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

// class to build the notification that is sent from app
class QuestCounterNotificationService(private val context: Context) {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager // creates notification manager
    private val activityIntent = Intent(context, MainActivity::class.java) // creates intent for pending intent
    // creates the pending intent for notification
    private val pendingIntent: PendingIntent = PendingIntent.getActivity(
        context, // passes in context from application
        1,
        activityIntent, // passes in activity intent
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) // checks if the proper build version is installed and sets pending intent flag to immutable
            PendingIntent.FLAG_IMMUTABLE
        else
            0
    )

    // companion object used to id the notification channel
    companion object{
        const val QUEST_COUNTER_CHANNEL_ID = "quest_counter_channel"
    }

    // constructs notification and displays it to the user
    // takes in the quest counter (number of pending quests) from the quest list view model
    fun showNotification(questCounter: Int){
        if(questCounter > 0){ // checks if the count is greater than 0
            val notification = NotificationCompat.Builder(context, QUEST_COUNTER_CHANNEL_ID) // instantiates notification object to be built
                .setSmallIcon(R.drawable.baseline_checklist_24) // sets the icon of the notification
                .setContentTitle("Quests Pending Count") // titles the notification
                .setContentText("The total number of quests pending is $questCounter") // sets the text for the notification
                .setContentIntent(pendingIntent) // sets the intent for the notification
                .setSilent(true) // makes notification silent
                .build() // builds the notification

            notificationManager.notify(1, notification) // sends notification
        } else {
            val notification = NotificationCompat.Builder(context, QUEST_COUNTER_CHANNEL_ID) // instantiates notification object to be built
                .setSmallIcon(R.drawable.baseline_checklist_24) // sets the icon of the notification
                .setContentTitle("Quests Pending Count") // titles the notification
                .setContentText("You have no quest pending at the moment. Add a quest to hack a habit.") // sets the text for the notification
                .setContentIntent(pendingIntent) // sets the intent for the notification
                .setSilent(true) // makes notification silent
                .build() // builds the notification

            notificationManager.notify(1, notification) // sends notification
        }

    }
}