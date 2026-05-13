package com.sip.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WaterDrop

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.sip.ui.theme.SipShapes

@Composable
fun QuickAddTile(
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Default.WaterDrop
) {

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp)
            .clickable {
                onClick()
            },

        shape = SipShapes.Pill,

        tonalElevation = 1.dp,

        color =
            MaterialTheme
                .colorScheme
                .surfaceContainer
    ) {

        Column(
            modifier = Modifier
                .padding(vertical = 8.dp),

            verticalArrangement =
                Arrangement.spacedBy(2.dp),

            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            Icon(
                imageVector = icon,
                contentDescription = null,

                modifier = Modifier
                    .size(14.dp)
            )

            Text(
                text = title,

                style = MaterialTheme
                    .typography
                    .titleSmall
            )

            Text(
                text = subtitle,

                style = MaterialTheme
                    .typography
                    .labelSmall,

                color =
                    MaterialTheme
                        .colorScheme
                        .onSurfaceVariant
            )
        }
    }
}