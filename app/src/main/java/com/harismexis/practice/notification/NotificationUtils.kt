package com.harismexis.practice.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

const val NOTIFICATION_CHANNEL_ID = "10"
const val NOTIFICATION_CHANNEL_NAME = "default_notification_channel"
const val NOTIFICATION_CHANNEL_DESCRIPTION = "Default Notification Channel"

fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = NOTIFICATION_CHANNEL_NAME // getString(R.string.channel_name)
        val descriptionText =
            NOTIFICATION_CHANNEL_DESCRIPTION // getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance)
            .also {
                it.description = descriptionText
            }
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}