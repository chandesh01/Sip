package com.sip.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.sip.R

@Composable
fun ReminderIntervalSection(
    intervalMinutes: Long,
    onIntervalChanged: (Long) -> Unit
) {

    val isCustomInterval =
        intervalMinutes != 30L &&
                intervalMinutes != 60L

    var customExpanded by remember {
        mutableStateOf(false)
    }

    var customInterval by remember(
        intervalMinutes
    ) {
        mutableStateOf(
            if (isCustomInterval) {
                intervalMinutes.toString()
            } else {
                ""
            }
        )
    }

    androidx.compose.foundation.layout.Column(

        verticalArrangement =
            Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Reminder Interval",

            style = MaterialTheme
                .typography
                .titleMedium
        )

        Row(
            horizontalArrangement =
                Arrangement.spacedBy(12.dp)
        ) {

            FilterChip(
                selected = intervalMinutes == 30L,

                onClick = {

                    customExpanded = false

                    onIntervalChanged(30L)
                },

                label = {
                    Text("30 mins")
                }
            )

            FilterChip(
                selected = intervalMinutes == 60L,

                onClick = {

                    customExpanded = false

                    onIntervalChanged(60L)
                },

                label = {
                    Text("60 mins")
                }
            )

            FilterChip(
                selected = isCustomInterval,

                onClick = {

                    customExpanded = !customExpanded
                },

                leadingIcon = {

                    Icon(
                        imageVector =
                            Icons.Default.Edit,

                        contentDescription = null
                    )
                },

                label = {

                    Text(
                        if (
                            customInterval.isNotBlank()
                        ) {
                            "${customInterval} mins"
                        } else {
                            "Custom"
                        }
                    )
                }
            )
        }

        if (customExpanded) {

            val value =
                customInterval.toLongOrNull()

            val isValid =
                value != null &&
                        value in 1L..720L

            val showError =
                customInterval.isNotBlank() &&
                        !isValid

            Row(
                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.spacedBy(12.dp),

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                OutlinedTextField(
                    value = customInterval,

                    onValueChange = {
                        customInterval = it
                    },

                    placeholder = {
                        Text("Minutes")
                    },

                    keyboardOptions =
                        KeyboardOptions(
                            keyboardType =
                                KeyboardType.Number
                        ),

                    singleLine = true,

                    isError = showError,

                    supportingText = {

                        if (showError) {

                            Text(
                                text =
                                    "Enter between 15 and 720 mins"
                            )
                        }
                    },

                    modifier = Modifier
                        .weight(1f)
                )

                FilledTonalButton(

                    enabled = isValid,

                    onClick = {

                        onIntervalChanged(value!!)

                        customExpanded = false
                    },

                    modifier = Modifier
                        .height(56.dp)
                ) {

                    Text(
                        stringResource(R.string.ok)
                    )
                }
            }
        }
    }
}