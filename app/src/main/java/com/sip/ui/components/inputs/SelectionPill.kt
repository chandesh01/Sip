package com.sip.ui.components.inputs

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

import com.sip.ui.theme.SipShapes

@Composable
fun <T> SelectionPill(
    selectedItem: T,

    items: List<T>,

    label: (T) -> String,

    onItemSelected: (T) -> Unit
) {

    var expanded by remember {

        mutableStateOf(false)
    }

    Box(

        modifier = Modifier
            .wrapContentWidth()

            .clip(
                SipShapes.Pill
            )

            .clickable {

                expanded = true
            }
    ) {

        Surface(

            shape =
                SipShapes.Pill,

            tonalElevation = 2.dp,

            color =
                MaterialTheme
                    .colorScheme
                    .surfaceVariant,

            modifier = Modifier
                .border(
                    width = 1.dp,

                    color =
                        MaterialTheme
                            .colorScheme
                            .outlineVariant,

                    shape =
                        SipShapes.Pill
                )
        ) {

            Row(

                modifier = Modifier
                    .padding(
                        horizontal = 18.dp,
                        vertical = 12.dp
                    ),

                verticalAlignment =
                    Alignment.CenterVertically,

                horizontalArrangement =
                    Arrangement.spacedBy(6.dp)
            ) {

                Text(
                    text =
                        label(selectedItem),

                    style =
                        MaterialTheme
                            .typography
                            .titleSmall
                )

                Icon(
                    imageVector =
                        Icons.Default
                            .ArrowDropDown,

                    contentDescription = null
                )
            }
        }

        DropdownMenu(
            expanded = expanded,

            onDismissRequest = {

                expanded = false
            }
        ) {

            items.forEach { item ->

                DropdownMenuItem(

                    text = {

                        Text(
                            label(item)
                        )
                    },

                    onClick = {

                        expanded = false

                        onItemSelected(item)
                    }
                )
            }
        }
    }
}