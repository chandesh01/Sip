package com.sip.ui.settings

import android.app.TimePickerDialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun NotificationDurationSection(
    startTime: String,
    endTime: String,

    onStartTimeChanged: (String) -> Unit,
    onEndTimeChanged: (String) -> Unit
) {

    val context = LocalContext.current

    androidx.compose.foundation.layout.Column(

        verticalArrangement =
            Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = "Notification Duration",

            style = MaterialTheme
                .typography
                .titleMedium
        )

        Row(
            horizontalArrangement =
                Arrangement.spacedBy(12.dp),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            TimeButton(
                time = startTime,

                onClick = {

                    TimePickerDialog(
                        context,

                        { _,
                          hour,
                          minute ->

                            onStartTimeChanged(
                                String.format(
                                    "%02d:%02d",
                                    hour,
                                    minute
                                )
                            )
                        },

                        8,
                        0,
                        true
                    ).show()
                }
            )

            Text("-")

            TimeButton(
                time = endTime,

                onClick = {

                    TimePickerDialog(
                        context,

                        { _,
                          hour,
                          minute ->

                            onEndTimeChanged(
                                String.format(
                                    "%02d:%02d",
                                    hour,
                                    minute
                                )
                            )
                        },

                        22,
                        0,
                        true
                    ).show()
                }
            )
        }
    }
}