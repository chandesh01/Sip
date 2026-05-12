package com.sip.ui.settings

import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable

@Composable
fun TimeButton(
    time: String,
    onClick: () -> Unit
) {

    FilterChip(
        selected = false,

        onClick = onClick,

        label = {

            Text(
                text = time
            )
        }
    )
}