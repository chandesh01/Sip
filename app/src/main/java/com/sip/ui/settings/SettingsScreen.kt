package com.sip.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

import com.sip.MainActivity
import com.sip.ReminderScheduler

import com.sip.ui.components.cards.SipCard
import com.sip.ui.components.layout.ScreenContainer
import com.sip.ui.components.layout.ScreenHeader
import com.sip.ui.theme.SipShapes

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

    var showPermissionDialog by remember {

        mutableStateOf(false)
    }

    /*
    ---------------------------------------------------
    UI
    ---------------------------------------------------
    */

    ScreenContainer(
        paddingValues = paddingValues
    ) {

        /*
        ---------------------------------------------------
        HEADER
        ---------------------------------------------------
        */

        item {

            ScreenHeader(
                title = "Settings"
            )
        }

        /*
        ---------------------------------------------------
        REMINDERS CARD
        ---------------------------------------------------
        */

        item {

            SipCard {

                Column(

                    verticalArrangement =
                        Arrangement.spacedBy(24.dp)
                ) {

                    /*
                    ---------------------------------------------------
                    HEADER
                    ---------------------------------------------------
                    */

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),

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
                            checked =
                                remindersEnabled,

                            onCheckedChange = { enabled ->

                                /*
                                ---------------------------------------------------
                                SAVE STATE
                                ---------------------------------------------------
                                */

                                viewModel
                                    .setRemindersEnabled(
                                        enabled
                                    )

                                /*
                                ---------------------------------------------------
                                ENABLE
                                ---------------------------------------------------
                                */

                                if (enabled) {

                                    showPermissionDialog = true
                                }

                                /*
                                ---------------------------------------------------
                                DISABLE
                                ---------------------------------------------------
                                */

                                else {

                                    ReminderScheduler
                                        .stopReminders(
                                            context
                                        )
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
                                    .stopReminders(
                                        context
                                    )

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
                                    .stopReminders(
                                        context
                                    )

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
                                    .stopReminders(
                                        context
                                    )

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

                        style =
                            MaterialTheme
                                .typography
                                .bodyMedium
                    )
                }
            }
        }

        /*
        ---------------------------------------------------
        DAILY GOAL CARD
        ---------------------------------------------------
        */

        item {

            SipCard {

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

    /*
    ---------------------------------------------------
    PERMISSION DIALOG
    ---------------------------------------------------
    */

    if (showPermissionDialog) {

        AlertDialog(

            onDismissRequest = {

                showPermissionDialog = false
            },

            shape =
                SipShapes.LargeCard,

            title = {

                Text(
                    text =
                        "Enable Reliable Reminders"
                )
            },

            text = {

                Text(
                    text =
                        "Sip needs exact alarms and battery optimization disabled for reliable hydration reminders."
                )
            },

            confirmButton = {

                TextButton(

                    onClick = {

                        showPermissionDialog =
                            false

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

                                alarmManager
                                    .canScheduleExactAlarms()

                            } else {

                                true
                            }

                        if (canSchedule) {

                            ReminderScheduler
                                .startReminders(
                                    context = context,

                                    intervalMinutes =
                                        intervalMinutes
                                )
                        }
                    }
                ) {

                    Text("Continue")
                }
            },

            dismissButton = {

                TextButton(

                    onClick = {

                        showPermissionDialog =
                            false

                        viewModel
                            .setRemindersEnabled(
                                false
                            )
                    }
                ) {

                    Text("Cancel")
                }
            }
        )
    }
}