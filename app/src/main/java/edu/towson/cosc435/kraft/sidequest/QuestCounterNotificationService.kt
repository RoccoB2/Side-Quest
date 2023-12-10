package edu.towson.cosc435.kraft.sidequest

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

class QuestCounterNotificationService(private val context: Context) {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val activityIntent = Intent(context, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(
        context,
        1,
        activityIntent,
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            PendingIntent.FLAG_IMMUTABLE
        else
            0
    )
    companion object{
        const val QUEST_COUNTER_CHANNEL_ID = "quest_counter_channel"
    }

    fun showNotification(questCounter: Int){
        if(questCounter > 0){
            val notification = NotificationCompat.Builder(context, QUEST_COUNTER_CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_checklist_24)
                .setContentTitle("Quests Pending Count")
                .setContentText("The total number of quests pending is $questCounter")
                .setContentIntent(pendingIntent)
                .setSilent(true)
                .build()

            notificationManager.notify(1, notification)
        } else {
            val notification = NotificationCompat.Builder(context, QUEST_COUNTER_CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_checklist_24)
                .setContentTitle("Quests Pending Count")
                .setContentText("You have no quest pending at the moment. Add a quest to hack a habit.")
                .setContentIntent(pendingIntent)
                .setSilent(true)
                .build()

            notificationManager.notify(1, notification)
        }

    }
}