package com.sip.ui.settings

import androidx.compose.runtime.Composable

import com.sip.ui.components.inputs
.PresetWithCustomInputSection

@Composable
fun DailyGoalSection(
    dailyGoal: Long,
    onGoalChanged: (Long) -> Unit
) {

    PresetWithCustomInputSection(

        title = "Daily Goal",

        selectedValue = dailyGoal,

        presets =
            listOf(
                2000L,
                3000L
            ),

        valueSuffix = "ml",

        customLabel = "Custom",

        placeholder = "Custom goal",

        validRange = 500L..15000L,

        errorText =
            "Enter between 500 and 15000 ml",

        onValueSelected = onGoalChanged
    )
}