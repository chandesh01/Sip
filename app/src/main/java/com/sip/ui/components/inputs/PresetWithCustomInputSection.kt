package com.sip.ui.components.inputs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun PresetWithCustomInputSection(

    title: String,

    selectedValue: Long,

    presets: List<Long>,

    valueSuffix: String,

    customLabel: String,

    placeholder: String,

    validRange: LongRange,

    errorText: String,

    onValueSelected: (Long) -> Unit
) {

    /*
    ---------------------------------------------------
    CUSTOM STATE
    ---------------------------------------------------
    */

    val isCustomValue =
        selectedValue !in presets

    var customExpanded by remember {

        mutableStateOf(false)
    }

    var customValue by remember(
        selectedValue
    ) {

        mutableStateOf(

            if (isCustomValue) {

                selectedValue.toString()

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

    androidx.compose.foundation.layout.Column(

        verticalArrangement =
            Arrangement.spacedBy(16.dp)
    ) {

        /*
        ---------------------------------------------------
        TITLE
        ---------------------------------------------------
        */

        Text(
            text = title,

            style =
                MaterialTheme
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

            presets.forEach { preset ->

                FilterChip(
                    selected =
                        selectedValue == preset,

                    onClick = {

                        customExpanded = false

                        customValue = ""

                        onValueSelected(preset)
                    },

                    label = {

                        Text(
                            "$preset $valueSuffix"
                        )
                    }
                )
            }

            /*
            ---------------------------------------------------
            CUSTOM CHIP
            ---------------------------------------------------
            */

            FilterChip(
                selected = isCustomValue,

                onClick = {

                    customExpanded =
                        !customExpanded
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
                            customValue.isNotBlank()
                        ) {

                            "$customValue $valueSuffix"

                        } else {

                            customLabel
                        }
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
                customValue.toLongOrNull()

            val isValid =
                value != null &&
                        value in validRange

            val showError =
                customValue.isNotBlank() &&
                        !isValid

            Row(
                modifier = Modifier
                    .fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.spacedBy(12.dp),

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                OutlinedTextField(
                    value = customValue,

                    onValueChange = {

                        customValue = it
                    },

                    placeholder = {

                        Text(placeholder)
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

                            Text(errorText)
                        }
                    },

                    modifier = Modifier
                        .weight(1f)
                )

                FilledTonalButton(

                    enabled = isValid,

                    onClick = {

                        onValueSelected(
                            value!!
                        )

                        customExpanded = false
                    },

                    modifier = Modifier
                        .height(56.dp)
                ) {

                    Text("OK")
                }
            }
        }
    }
}