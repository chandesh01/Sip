package com.sip.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row

import androidx.compose.material3.Icon

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun QuickAddButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null
) {

    Button(
        onClick = onClick,

        modifier = modifier
            .height(56.dp),

        shape = RoundedCornerShape(24.dp),

        elevation =
            ButtonDefaults.buttonElevation(
                defaultElevation = 1.dp,
                pressedElevation = 2.dp
            ),

        contentPadding = PaddingValues(
            horizontal = 20.dp,
            vertical = 0.dp
        )
    ) {

        Row(
            horizontalArrangement =
                Arrangement.spacedBy(8.dp)
        ) {

            if (icon != null) {

                Icon(
                    imageVector = icon,
                    contentDescription = null
                )
            }

            Text(
                text = text,

                maxLines = 1,

                style = MaterialTheme
                    .typography
                    .labelLarge
            )
        }
    }
}