package com.sip.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.sip.MainActivity

import com.sip.ReminderScheduler

@Composable
fun SettingsScreen(
    paddingValues: PaddingValues,
    viewModel: SettingsViewModel
) {

    val context = LocalContext.current

    /*
    ---------------------------------------------------
    STATE
    ---------------------------------------------------
    */

    val remindersEnabled by
    viewModel
        .remindersEnabled
        .collectAsState()

    val intervalMinutes by
    viewModel
        .intervalMinutes
        .collectAsState()

    val startTime by
    viewModel
        .startTime
        .collectAsState()

    val endTime by
    viewModel
        .endTime
        .collectAsState()

    val dailyGoal by
    viewModel
        .dailyGoal
        .collectAsState()

    /*
    ---------------------------------------------------
    UI
    ---------------------------------------------------
    */

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 20.dp),

        verticalArrangement =
            Arrangement.spacedBy(16.dp)
    ) {

        /*
        ---------------------------------------------------
        HEADER
        ---------------------------------------------------
        */

        Text(
            text = "Settings",

            modifier = Modifier
                .padding(top = 12.dp),

            style = MaterialTheme
                .typography
                .headlineMedium
        )

        /*
        ---------------------------------------------------
        REMINDERS CARD
        ---------------------------------------------------
        */

        Card(
            modifier = Modifier.fillMaxWidth(),

            shape = RoundedCornerShape(28.dp)
        ) {

            Column(
                modifier = Modifier.padding(20.dp),

                verticalArrangement =
                    Arrangement.spacedBy(24.dp)
            ) {

                /*
                ---------------------------------------------------
                HEADER
                ---------------------------------------------------
                */

                Row(
                    modifier = Modifier.fillMaxWidth(),

                    horizontalArrangement =
                        Arrangement.SpaceBetween,

                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Text(
                        text = "Reminders",

                        style = MaterialTheme
                            .typography
                            .titleLarge
                    )

                    Switch(
                        checked = remindersEnabled,

                        onCheckedChange = { enabled ->

                            ReminderScheduler
                                .stopReminders(context)

                            viewModel
                                .setRemindersEnabled(enabled)

                            if (enabled) {

                                (context as? MainActivity)
                                    ?.requestExactAlarmPermission()

                                (context as? MainActivity)
                                    ?.requestBatteryOptimizationDisable()

                                val alarmManager =
                                    context.getSystemService(
                                        android.app.AlarmManager::class.java
                                    )

                                val canSchedule =

                                    if (
                                        android.os.Build.VERSION.SDK_INT >=
                                        android.os.Build.VERSION_CODES.S
                                    ) {

                                        alarmManager.canScheduleExactAlarms()

                                    } else {
                                        true
                                    }

                                if (canSchedule) {

                                    ReminderScheduler
                                        .startReminders(
                                            context = context,
                                            intervalMinutes = intervalMinutes
                                        )
                                }
                            } else {

                                ReminderScheduler
                                    .stopReminders(context)
                            }
                        }
                    )
                }

                /*
                ---------------------------------------------------
                REMINDER INTERVAL
                ---------------------------------------------------
                */

                ReminderIntervalSection(
                    intervalMinutes =
                        intervalMinutes,

                    onIntervalChanged = {

                        viewModel
                            .setIntervalMinutes(it)

                        if (remindersEnabled) {

                            ReminderScheduler
                                .stopReminders(context)

                            ReminderScheduler
                                .startReminders(
                                    context = context,

                                    intervalMinutes = it
                                )
                        }
                    }
                )

                HorizontalDivider()

                /*
                ---------------------------------------------------
                NOTIFICATION DURATION
                ---------------------------------------------------
                */

                NotificationDurationSection(
                    startTime = startTime,
                    endTime = endTime,

                    onStartTimeChanged = {

                        viewModel
                            .setStartTime(it)

                        if (remindersEnabled) {

                            ReminderScheduler
                                .stopReminders(context)

                            ReminderScheduler
                                .startReminders(
                                    context = context,
                                    intervalMinutes =
                                        intervalMinutes
                                )
                        }
                    },

                    onEndTimeChanged = {

                        viewModel
                            .setEndTime(it)

                        if (remindersEnabled) {

                            ReminderScheduler
                                .stopReminders(context)

                            ReminderScheduler
                                .startReminders(
                                    context = context,
                                    intervalMinutes =
                                        intervalMinutes
                                )
                        }
                    }
                )

                Text(
                    text =
                        "You’ll only receive reminders between start and stop times.",

                    style = MaterialTheme
                        .typography
                        .bodyMedium
                )
            }
        }

        /*
        ---------------------------------------------------
        DAILY GOAL CARD
        ---------------------------------------------------
        */

        Card(
            modifier = Modifier.fillMaxWidth(),

            shape = RoundedCornerShape(28.dp)
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                DailyGoalSection(
                    dailyGoal = dailyGoal,

                    onGoalChanged = {

                        viewModel
                            .setDailyGoal(it)
                    }
                )
            }
        }
    }
}