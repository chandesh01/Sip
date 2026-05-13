package com.sip.ui.settings

import androidx.compose.runtime.Composable

import com.sip.ui.components.inputs
.PresetWithCustomInputSection

@Composable
fun ReminderIntervalSection(
    intervalMinutes: Long,
    onIntervalChanged: (Long) -> Unit
) {

    PresetWithCustomInputSection(

        title = "Reminder Interval",

        selectedValue = intervalMinutes,

        presets =
            listOf(
                30L,
                60L
            ),

        valueSuffix = "mins",

        customLabel = "Custom",

        placeholder = "Minutes",

        validRange = 15L..720L,

        errorText =
            "Enter between 15 and 720 mins",

        onValueSelected =
            onIntervalChanged
    )
}