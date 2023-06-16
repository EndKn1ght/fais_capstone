package com.bangkit.bangkitcapstone.broadcast

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.bangkit.bangkitcapstone.R
import com.bangkit.bangkitcapstone.ui.activity.splash.MainActivity

class DailyReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Create and send the notification
        val notification = buildNotification(context)
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun buildNotification(context: Context): Notification {
        val channelId = "daily_reminder_channel"
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.fais_logo)
            .setContentTitle("Water Daily Reminder")
            .setContentText("Don't forget to Drink Water!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        notificationBuilder.setContentIntent(pendingIntent)

        return notificationBuilder.build()
    }

    companion object {
        private const val NOTIFICATION_ID = 1
    }
}
