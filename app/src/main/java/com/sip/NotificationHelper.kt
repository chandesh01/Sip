package com.sip

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager

import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificationHelper(
    private val context: Context
) {

    companion object {

        const val CHANNEL_ID =
            "water_reminders"

        const val NOTIFICATION_ID = 1
    }

    /*
    ---------------------------------------------------
    CHANNEL
    ---------------------------------------------------
    */

    fun createNotificationChannel() {

        val channel = NotificationChannel(

            CHANNEL_ID,

            "Water Reminders",

            NotificationManager
                .IMPORTANCE_DEFAULT

        ).apply {

            description =
                "Hydration reminder notifications"
        }

        val notificationManager =
            context.getSystemService(
                NotificationManager::class.java
            )

        notificationManager
            .createNotificationChannel(
                channel
            )
    }

    /*
    ---------------------------------------------------
    SHOW NOTIFICATION
    ---------------------------------------------------
    */

    fun showReminderNotification() {

        /*
        ---------------------------------------------------
        OPEN APP
        ---------------------------------------------------
        */

        val intent = Intent(
            context,
            MainActivity::class.java
        ).apply {

            flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        val pendingIntent =
            PendingIntent.getActivity(

                context,

                0,

                intent,

                PendingIntent.FLAG_UPDATE_CURRENT or
                        PendingIntent.FLAG_IMMUTABLE
            )

        /*
        ---------------------------------------------------
        NOTIFICATION
        ---------------------------------------------------
        */

        val notification =
            NotificationCompat.Builder(
                context,
                CHANNEL_ID
            )

                .setSmallIcon(
                    R.drawable.ic_notification
                )

                .setContentTitle("Sip")

                .setContentText(
                    "Time to drink water."
                )

                .setPriority(
                    NotificationCompat.PRIORITY_DEFAULT
                )

                .setDefaults(
                    NotificationCompat.DEFAULT_ALL
                )

                .setAutoCancel(true)

                .setContentIntent(
                    pendingIntent
                )

                .build()

        /*
        ---------------------------------------------------
        PERMISSION CHECK
        ---------------------------------------------------
        */

        if (
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission
                    .POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }

        /*
        ---------------------------------------------------
        SHOW
        ---------------------------------------------------
        */

        NotificationManagerCompat
            .from(context)
            .notify(
                NOTIFICATION_ID,
                notification
            )
    }
}