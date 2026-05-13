package com.sip.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

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

import androidx.compose.ui.text.style.TextAlign
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

            /*
            ---------------------------------------------------
            PERFECT GRID TILE
            ---------------------------------------------------
            */

            .aspectRatio(1f)

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

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp),

            contentAlignment = Alignment.Center
        ) {

            Column(

                horizontalAlignment =
                    Alignment.CenterHorizontally,

                verticalArrangement =
                    Arrangement.spacedBy(2.dp)
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
                        .titleSmall,

                    textAlign = TextAlign.Center
                )

                Text(
                    text = subtitle,

                    style = MaterialTheme
                        .typography
                        .labelSmall,

                    textAlign = TextAlign.Center,

                    color =
                        MaterialTheme
                            .colorScheme
                            .onSurfaceVariant
                )
            }
        }
    }
}