package com.sip.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height

import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit

import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilledTonalButton
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
fun DailyGoalSection(
    dailyGoal: Long,
    onGoalChanged: (Long) -> Unit
) {

    /*
    ---------------------------------------------------
    CUSTOM STATE
    ---------------------------------------------------
    */

    val isCustomGoal =
        dailyGoal != 2000L &&
                dailyGoal != 3000L

    var customExpanded by remember {
        mutableStateOf(false)
    }

    var customGoal by remember(
        dailyGoal
    ) {
        mutableStateOf(
            if (isCustomGoal) {
                dailyGoal.toString()
            } else {
                ""
            }
        )
    }

    /*
    ---------------------------------------------------
    UI
    ---------------------------------------------------
    */

    Column(

        verticalArrangement =
            Arrangement.spacedBy(12.dp)
    ) {

        /*
        ---------------------------------------------------
        TITLE
        ---------------------------------------------------
        */

        Text(
            text = "Daily Goal",

            style = MaterialTheme
                .typography
                .titleMedium
        )

        /*
        ---------------------------------------------------
        CHIPS
        ---------------------------------------------------
        */

        FlowRow(

            horizontalArrangement =
                Arrangement.spacedBy(12.dp),

            verticalArrangement =
                Arrangement.spacedBy(12.dp)
        ) {

            FilterChip(
                selected = dailyGoal == 2000L,

                onClick = {

                    customExpanded = false

                    onGoalChanged(2000L)
                },

                label = {

                    Text(
                        text = "2000 ml",

                        style = MaterialTheme
                            .typography
                            .labelMedium
                    )
                }
            )

            FilterChip(
                selected = dailyGoal == 3000L,

                onClick = {

                    customExpanded = false

                    onGoalChanged(3000L)
                },

                label = {

                    Text(
                        text = "3000 ml",

                        style = MaterialTheme
                            .typography
                            .labelMedium
                    )
                }
            )


            FilterChip(
                selected = isCustomGoal,

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

                        text = if (
                            customGoal.isNotBlank()
                        ) {
                            "${customGoal} ml"
                        } else {
                            "Custom"
                        },

                        style = MaterialTheme
                            .typography
                            .labelMedium
                    )
                }
            )
        }

        /*
        ---------------------------------------------------
        CUSTOM INPUT
        ---------------------------------------------------
        */

        if (customExpanded) {

            val value =
                customGoal.toLongOrNull()

            val isValid =
                value != null &&
                        value in 500L..15000L

            val showError =
                customGoal.isNotBlank() &&
                        !isValid

            Row(
                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.spacedBy(12.dp),

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                OutlinedTextField(
                    value = customGoal,

                    onValueChange = {
                        customGoal = it
                    },

                    placeholder = {

                        Text(
                            text = "Custom goal"
                        )
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
                                    "Enter between 500 and 15000 ml"
                            )
                        }
                    },

                    modifier = Modifier
                        .weight(1f)
                )

                FilledTonalButton(

                    enabled = isValid,

                    onClick = {

                        onGoalChanged(value!!)

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