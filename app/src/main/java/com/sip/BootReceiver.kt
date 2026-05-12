package com.sip

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

import com.sip.data.settings.SettingsPreferences

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(
        context: Context,
        intent: Intent
    ) {

        if (
            intent.action ==
            Intent.ACTION_BOOT_COMPLETED ||

            intent.action ==
            Intent.ACTION_MY_PACKAGE_REPLACED
        ) {

            CoroutineScope(
                Dispatchers.IO
            ).launch {

                val preferences =
                    SettingsPreferences(context)

                val remindersEnabled =
                    preferences
                        .remindersEnabled
                        .first()

                if (remindersEnabled) {

                    val intervalMinutes =
                        preferences
                            .intervalMinutes
                            .first()

                    ReminderScheduler
                        .startReminders(
                            context = context,
                            intervalMinutes =
                                intervalMinutes
                        )
                }
            }
        }
    }
}