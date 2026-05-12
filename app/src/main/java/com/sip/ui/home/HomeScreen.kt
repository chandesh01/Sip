package com.sip.ui.home

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.sip.R
import com.sip.ui.components.QuickAddButton
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

    var showPeriodMenu by remember {
        mutableStateOf(false)
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

    val todayDate =
        formatter.format(
            Calendar
                .getInstance()
                .time
        )

    val weekStartDate =
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

    val monthStartDate =
        formatter.format(

            Calendar.getInstance().apply {

                set(
                    Calendar.DAY_OF_MONTH,
                    1
                )

            }.time
        )

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

            (todayTotal.toFloat() / dailyGoal.toFloat())
                .coerceIn(0f, 1f)

        } else {

            0f
        }

    val subtitle =
        when (selectedPeriod) {

            DashboardPeriod.DAY -> {

                if (dailyGoal > 0) {

                    "Goal: $dailyGoal ml • ${
                        (todayTotal * 100) / dailyGoal.toInt()
                    }%"

                } else {

                    "Goal: $dailyGoal ml"
                }
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

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 20.dp),

        verticalArrangement =
            Arrangement.spacedBy(20.dp)
    ) {

        /*
        ---------------------------------------------------
        HEADER
        ---------------------------------------------------
        */

        item {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),

                horizontalArrangement =
                    Arrangement.SpaceBetween,

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Text(
                    text = "Sip",

                    style = MaterialTheme
                        .typography
                        .headlineLarge
                )

                Box(
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(24.dp)
                        )
                        .clickable {
                            showPeriodMenu = true
                        }
                ) {

                    Surface(

                        shape =
                            RoundedCornerShape(24.dp),

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
                                    RoundedCornerShape(24.dp)
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
                                    when (selectedPeriod) {

                                        DashboardPeriod.DAY ->
                                            "Today"

                                        DashboardPeriod.WEEK ->
                                            "1 Week"

                                        DashboardPeriod.MONTH ->
                                            "1 Month"
                                    },

                                style = MaterialTheme
                                    .typography
                                    .titleSmall
                            )

                            Icon(
                                imageVector =
                                    Icons.Default
                                        .ArrowDropDown,

                                contentDescription = null,

                                modifier = Modifier
                                    .size(18.dp)
                            )
                        }
                    }

                    DropdownMenu(
                        expanded = showPeriodMenu,

                        onDismissRequest = {
                            showPeriodMenu = false
                        }
                    ) {

                        DropdownMenuItem(

                            text = {
                                Text("Today")
                            },

                            onClick = {

                                showPeriodMenu = false

                                selectedPeriod =
                                    DashboardPeriod.DAY
                            }
                        )

                        DropdownMenuItem(

                            text = {
                                Text("1 Week")
                            },

                            onClick = {

                                showPeriodMenu = false

                                selectedPeriod =
                                    DashboardPeriod.WEEK
                            }
                        )

                        DropdownMenuItem(

                            text = {
                                Text("1 Month")
                            },

                            onClick = {

                                showPeriodMenu = false

                                selectedPeriod =
                                    DashboardPeriod.MONTH
                            }
                        )
                    }
                }
            }
        }

        /*
        ---------------------------------------------------
        STATS CARD
        ---------------------------------------------------
        */

        item {

            Card(
                modifier = Modifier
                    .fillMaxWidth(),

                shape = RoundedCornerShape(32.dp)
            ) {

                Column(
                    modifier = Modifier
                        .padding(24.dp),

                    verticalArrangement =
                        Arrangement.spacedBy(14.dp)
                ) {

                    Text(
                        text = title,

                        style = MaterialTheme
                            .typography
                            .titleMedium
                    )

                    Text(
                        text = dateRange,

                        style = MaterialTheme
                            .typography
                            .bodySmall
                    )

                    Text(
                        text = "$amount ml",

                        style = MaterialTheme
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
                                .clip(
                                    RoundedCornerShape(
                                        999.dp
                                    )
                                )
                        )
                    }

                    Text(
                        text = subtitle,

                        style = MaterialTheme
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

            Row(
                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.spacedBy(12.dp)
            ) {

                QuickAddButton(
                    text = "300 ml",

                    modifier = Modifier.weight(1f),

                    onClick = {
                        viewModel.addWater(300)
                    }
                )

                QuickAddButton(
                    text = "500 ml",

                    modifier = Modifier.weight(1f),

                    onClick = {
                        viewModel.addWater(500)
                    }
                )

                QuickAddButton(
                    text = "1 L",

                    modifier = Modifier.weight(1f),

                    onClick = {
                        viewModel.addWater(1000)
                    }
                )

                QuickAddButton(
                    text = "",

                    icon = Icons.Default.Edit,


                    modifier = Modifier.weight(1f),

                    onClick = {
                        showCustomDialog = true
                    }
                )
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

            shape = RoundedCornerShape(32.dp),

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

                    shape = RoundedCornerShape(20.dp),

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

                        viewModel.addWater(amount!!)

                        customAmount = ""

                        showCustomDialog = false
                    }
                ) {

                    Text(
                        stringResource(R.string.ok)
                    )
                }
            },

            dismissButton = {

                TextButton(

                    onClick = {

                        customAmount = ""

                        showCustomDialog = false
                    }
                ) {

                    Text("Cancel")
                }
            }
        )
    }
}