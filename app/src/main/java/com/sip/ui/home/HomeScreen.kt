package com.sip.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.sip.R
import com.sip.ui.components.QuickAddTile
import com.sip.ui.components.cards.SipCard
import com.sip.ui.components.inputs.SelectionPill
import com.sip.ui.components.layout.ScreenContainer
import com.sip.ui.components.layout.ScreenHeader
import com.sip.ui.theme.SipShapes
import java.text.SimpleDateFormat
import java.util.Calendar

@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    viewModel: HomeViewModel,
    dailyGoal: Long
) {

    /*
    ---------------------------------------------------
    PERIOD
    ---------------------------------------------------
    */

    var selectedPeriod by remember {

        mutableStateOf(
            DashboardPeriod.DAY
        )
    }

    /*
    ---------------------------------------------------
    CUSTOM DIALOG
    ---------------------------------------------------
    */

    var showCustomDialog by remember {

        mutableStateOf(false)
    }

    var customAmount by remember {

        mutableStateOf("")
    }

    /*
    ---------------------------------------------------
    STATE
    ---------------------------------------------------
    */

    val todayTotal by
    viewModel
        .todayTotal
        .collectAsState()

    val weekTotal by
    viewModel
        .weekTotal
        .collectAsState()

    val weekAverage by
    viewModel
        .weekAverage
        .collectAsState()

    val monthTotal by
    viewModel
        .monthTotal
        .collectAsState()

    val monthAverage by
    viewModel
        .monthAverage
        .collectAsState()

    /*
    ---------------------------------------------------
    DATE FORMAT
    ---------------------------------------------------
    */

    val formatter = remember {

        SimpleDateFormat(
            "dd MMM yyyy",
            java.util.Locale.getDefault()
        )
    }

    val todayDate = remember {

        formatter.format(
            Calendar
                .getInstance()
                .time
        )
    }

    val weekStartDate = remember {

        formatter.format(

            Calendar.getInstance().apply {

                firstDayOfWeek =
                    Calendar.MONDAY

                set(
                    Calendar.DAY_OF_WEEK,
                    Calendar.MONDAY
                )

            }.time
        )
    }

    val monthStartDate = remember {

        formatter.format(

            Calendar.getInstance().apply {

                set(
                    Calendar.DAY_OF_MONTH,
                    1
                )

            }.time
        )
    }

    /*
    ---------------------------------------------------
    DISPLAY VALUES
    ---------------------------------------------------
    */

    val title =
        when (selectedPeriod) {

            DashboardPeriod.DAY ->
                "Today"

            DashboardPeriod.WEEK ->
                "This Week"

            DashboardPeriod.MONTH ->
                "This Month"
        }

    val dateRange =
        when (selectedPeriod) {

            DashboardPeriod.DAY -> {

                todayDate
            }

            DashboardPeriod.WEEK -> {

                "$weekStartDate - $todayDate"
            }

            DashboardPeriod.MONTH -> {

                "$monthStartDate - $todayDate"
            }
        }

    val amount =
        when (selectedPeriod) {

            DashboardPeriod.DAY ->
                todayTotal

            DashboardPeriod.WEEK ->
                weekTotal

            DashboardPeriod.MONTH ->
                monthTotal
        }

    val progress =
        if (dailyGoal > 0) {

            (
                    todayTotal.toFloat() /
                            dailyGoal.toFloat()
                    )
                .coerceIn(0f, 1f)

        } else {

            0f
        }

    val percentage =
        if (dailyGoal > 0) {

            (
                    (todayTotal * 100) /
                            dailyGoal.toInt()
                    )
                .coerceAtMost(100)

        } else {

            0
        }

    val subtitle =
        when (selectedPeriod) {

            DashboardPeriod.DAY -> {

                "Goal: $dailyGoal ml • $percentage%"
            }

            DashboardPeriod.WEEK -> {

                "Avg: ${weekAverage.toInt()} ml/day • Goal: $dailyGoal ml"
            }

            DashboardPeriod.MONTH -> {

                "Avg: ${monthAverage.toInt()} ml/day • Goal: $dailyGoal ml"
            }
        }

    /*
    ---------------------------------------------------
    UI
    ---------------------------------------------------
    */

    ScreenContainer(
        paddingValues = paddingValues
    ) {

        /*
        ---------------------------------------------------
        HEADER
        ---------------------------------------------------
        */

        item {

            ScreenHeader(
                title = "Sip",

                trailingContent = {

                    SelectionPill(
                        selectedItem = selectedPeriod,

                        items =
                            DashboardPeriod.entries,

                        label = {
                            it.label
                        },

                        onItemSelected = {

                            selectedPeriod = it
                        }
                    )
                }
            )
        }

        /*
        ---------------------------------------------------
        STATS CARD
        ---------------------------------------------------
        */

        item {

            SipCard {

                Column(

                    verticalArrangement =
                        Arrangement.spacedBy(
                            14.dp
                        )
                ) {

                    Text(
                        text = title,

                        style =
                            MaterialTheme
                                .typography
                                .titleMedium
                    )

                    Text(
                        text = dateRange,

                        style =
                            MaterialTheme
                                .typography
                                .bodySmall
                    )

                    Text(
                        text = "$amount ml",

                        style =
                            MaterialTheme
                                .typography
                                .displayMedium
                    )

                    if (
                        selectedPeriod ==
                        DashboardPeriod.DAY
                    ) {

                        LinearProgressIndicator(

                            progress = {
                                progress
                            },

                            modifier = Modifier
                                .fillMaxWidth()

                                .height(8.dp)

                                .clip(
                                    SipShapes.Pill
                                ),

                            drawStopIndicator = {},

                            gapSize = 0.dp
                        )
                    }

                    Text(
                        text = subtitle,

                        style =
                            MaterialTheme
                                .typography
                                .bodyMedium
                    )
                }
            }
        }

        /*
        ---------------------------------------------------
        QUICK ADD
        ---------------------------------------------------
        */

        item {

            SipCard {

                Column(

                    verticalArrangement =
                        Arrangement.spacedBy(
                            18.dp
                        )
                ) {

                    Text(
                        text = "Add Water",

                        style =
                            MaterialTheme
                                .typography
                                .titleLarge
                    )

                    LazyVerticalGrid(

                        columns =
                            GridCells.Fixed(4),

                        horizontalArrangement =
                            Arrangement.spacedBy(10.dp),

                        verticalArrangement =
                            Arrangement.spacedBy(10.dp),

                        modifier = Modifier
                            .height(170.dp),

                        userScrollEnabled = false
                    ) {

                        item {

                            QuickAddTile(
                                title = "250",
                                subtitle = "ml",

                                onClick = {

                                    viewModel.addWater(250)
                                }
                            )
                        }

                        item {

                            QuickAddTile(
                                title = "500",
                                subtitle = "ml",

                                onClick = {

                                    viewModel.addWater(500)
                                }
                            )
                        }

                        item {

                            QuickAddTile(
                                title = "750",
                                subtitle = "ml",

                                onClick = {

                                    viewModel.addWater(750)
                                }
                            )
                        }

                        item {

                            QuickAddTile(
                                title = "1 L",
                                subtitle = "Bottle",

                                onClick = {

                                    viewModel.addWater(1000)
                                }
                            )
                        }

                        item {

                            QuickAddTile(
                                title = "1.5",
                                subtitle = "L",

                                onClick = {

                                    viewModel.addWater(1500)
                                }
                            )
                        }

                        item {

                            QuickAddTile(
                                title = "2",
                                subtitle = "L",

                                onClick = {

                                    viewModel.addWater(2000)
                                }
                            )
                        }

                        item {

                            QuickAddTile(
                                title = "Tea",
                                subtitle = "200 ml",

                                onClick = {

                                    viewModel.addWater(200)
                                }
                            )
                        }

                        item {

                            QuickAddTile(
                                title = "+",
                                subtitle = "Custom",

                                onClick = {

                                    showCustomDialog = true
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    /*
    ---------------------------------------------------
    CUSTOM DIALOG
    ---------------------------------------------------
    */

    if (showCustomDialog) {

        val amount =
            customAmount.toIntOrNull()

        val isValid =
            amount != null &&
                    amount in 1..5000

        val showError =
            customAmount.isNotBlank() &&
                    !isValid

        AlertDialog(

            onDismissRequest = {

                showCustomDialog = false
            },

            shape =
                SipShapes.LargeCard,

            title = {

                Text(
                    text = "Custom Amount"
                )
            },

            text = {

                OutlinedTextField(
                    value = customAmount,

                    onValueChange = {

                        customAmount = it
                    },

                    placeholder = {

                        Text(
                            stringResource(
                                R.string.enter_in_ml
                            )
                        )
                    },

                    singleLine = true,

                    shape =
                        SipShapes.Pill,

                    keyboardOptions =
                        KeyboardOptions(
                            keyboardType =
                                KeyboardType.Number
                        ),

                    isError = showError,

                    supportingText = {

                        if (showError) {

                            Text(
                                text =
                                    "Enter between 1 and 5000 ml"
                            )
                        }
                    }
                )
            },

            confirmButton = {

                TextButton(

                    enabled = isValid,

                    onClick = {

                        viewModel
                            .addWater(amount!!)

                        customAmount = ""

                        showCustomDialog =
                            false
                    }
                ) {

                    Text(
                        stringResource(
                            R.string.ok
                        )
                    )
                }
            },

            dismissButton = {

                TextButton(

                    onClick = {

                        customAmount = ""

                        showCustomDialog =
                            false
                    }
                ) {

                    Text("Cancel")
                }
            }
        )
    }
}