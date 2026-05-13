package com.sip

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

import com.sip.data.settings.SettingsPreferences

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

import java.util.Calendar

class ReminderReceiver : BroadcastReceiver() {

    override fun onReceive(
        context: Context,
        intent: Intent?
    ) {

        CoroutineScope(
            Dispatchers.IO
        ).launch {

            /*
            ---------------------------------------------------
            LOAD SETTINGS
            ---------------------------------------------------
            */

            val preferences =
                SettingsPreferences(context)

            val startTime =
                preferences
                    .startTime
                    .first()

            val endTime =
                preferences
                    .endTime
                    .first()

            /*
            ---------------------------------------------------
            CURRENT TIME
            ---------------------------------------------------
            */

            val now =
                Calendar.getInstance()

            val currentMinutes =
                now.get(Calendar.HOUR_OF_DAY) * 60 +
                        now.get(Calendar.MINUTE)

            /*
            ---------------------------------------------------
            START TIME
            ---------------------------------------------------
            */

            val startParts =
                startTime.split(":")

            val startMinutes =
                startParts[0].toInt() * 60 +
                        startParts[1].toInt()

            /*
            ---------------------------------------------------
            END TIME
            ---------------------------------------------------
            */

            val endParts =
                endTime.split(":")

            val endMinutes =
                endParts[0].toInt() * 60 +
                        endParts[1].toInt()

            /*
            ---------------------------------------------------
            CHECK RANGE
            ---------------------------------------------------
            */

            val isWithinRange =

                /*
                ---------------------------------------------------
                SAME START + END
                ---------------------------------------------------
                */

                if (startMinutes == endMinutes) {

                    true

                }

                /*
                ---------------------------------------------------
                NORMAL RANGE
                ---------------------------------------------------
                */

                else if (startMinutes <= endMinutes) {

                    currentMinutes in
                            startMinutes..endMinutes

                }

                /*
                ---------------------------------------------------
                OVERNIGHT RANGE
                ---------------------------------------------------
                */

                else {

                    currentMinutes in
                            startMinutes..1439 ||

                            currentMinutes in
                            0..endMinutes
                }

            /*
            ---------------------------------------------------
            SHOW NOTIFICATION
            ---------------------------------------------------
            */

            if (isWithinRange) {

                NotificationHelper(context)
                    .showReminderNotification()
            }

            /*
            ---------------------------------------------------
            SCHEDULE NEXT
            ---------------------------------------------------
            */

            ReminderScheduler
                .scheduleNextReminder(context)
        }
    }
}