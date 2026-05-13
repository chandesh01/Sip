package com.sip.ui.stats

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLocale
import androidx.compose.ui.unit.dp
import com.sip.ui.components.cards.SipCard
import com.sip.ui.components.inputs.SelectionPill
import com.sip.ui.components.layout.ScreenContainer
import com.sip.ui.components.layout.ScreenHeader

@Composable
fun StatsScreen(
    paddingValues: PaddingValues,
    viewModel: StatsViewModel
) {

    /*
    ---------------------------------------------------
    STATE
    ---------------------------------------------------
    */

    val selectedRange by
    viewModel
        .selectedRange
        .collectAsState()

    val stats by
    viewModel
        .stats
        .collectAsState()

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
                title = "Stats",

                trailingContent = {

                    SelectionPill(
                        selectedItem = selectedRange,

                        items = StatsRange.entries,

                        label = {
                            it.label
                        },

                        onItemSelected = {

                            viewModel.setRange(it)
                        }
                    )
                }
            )
        }

        /*
        ---------------------------------------------------
        SUMMARY CARD
        ---------------------------------------------------
        */

        item {

            SummaryCard(
                title =

                    if (
                        selectedRange ==
                        StatsRange.WEEK
                    ) {

                        "Last 7 Days"

                    } else {

                        java.text.SimpleDateFormat(
                            "MMMM yyyy",
                            LocalLocale.current.platformLocale
                        ).format(
                            java.util.Date()
                        )
                    },

                total =
                    "${stats.total} ml",

                average =
                    "${stats.average} ml/day average"
            )
        }

        /*
        ---------------------------------------------------
        INSIGHTS
        ---------------------------------------------------
        */

        item {

            InsightRow(
                average =
                    "${stats.average} ml",

                total =
                    "${stats.total} ml",

                bestDay =
                    "${stats.bestDay} ml",

                totalSubtitle =

                    if (
                        selectedRange ==
                        StatsRange.WEEK
                    ) {

                        "last 7 days"

                    } else {

                        "this month"
                    }
            )
        }
    }
}

/*
---------------------------------------------------
SUMMARY CARD
---------------------------------------------------
*/

@Composable
fun SummaryCard(
    title: String,
    total: String,
    average: String
) {

    SipCard {

        Column(

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
                text = total,

                style = MaterialTheme
                    .typography
                    .displayMedium
            )

            Text(
                text = average,

                style = MaterialTheme
                    .typography
                    .bodyMedium
            )
        }
    }
}

/*
---------------------------------------------------
INSIGHT ROW
---------------------------------------------------
*/

@Composable
fun InsightRow(
    average: String,
    total: String,
    bestDay: String,
    totalSubtitle: String
) {

    androidx.compose.foundation.layout.FlowRow(

        modifier = Modifier
            .fillMaxWidth(),

        horizontalArrangement =
            Arrangement.spacedBy(12.dp),

        verticalArrangement =
            Arrangement.spacedBy(12.dp),

        maxItemsInEachRow = 2
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth(0.48f)
        ) {

            InsightCard(
                title = "Average",

                value = average,

                subtitle = "per day"
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth(0.48f)
        ) {

            InsightCard(
                title = "Total",

                value = total,

                subtitle = totalSubtitle
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth(0.48f)
        ) {

            InsightCard(
                title = "Best Day",

                value = bestDay,

                subtitle = ""
            )
        }
    }
}

/*
---------------------------------------------------
INSIGHT CARD
---------------------------------------------------
*/

@Composable
fun InsightCard(
    title: String,
    value: String,
    subtitle: String
) {

    SipCard {

        Column(

            verticalArrangement =
                Arrangement.spacedBy(8.dp)
        ) {

            Text(
                text = title,

                style = MaterialTheme
                    .typography
                    .bodySmall
            )

            Text(
                text = value,

                style = MaterialTheme
                    .typography
                    .headlineSmall
            )

            Text(
                text = subtitle,

                style = MaterialTheme
                    .typography
                    .bodySmall
            )
        }
    }
}