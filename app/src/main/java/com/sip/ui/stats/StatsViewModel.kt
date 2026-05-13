package com.sip.ui.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.sip.data.WaterDao

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn

import java.util.Calendar

/*
---------------------------------------------------
RANGE
---------------------------------------------------
*/

enum class StatsRange(
    val label: String
) {

    WEEK("7 Days"),

    MONTH("1 Month")
}

/*
---------------------------------------------------
STATS SUMMARY MODEL
---------------------------------------------------
*/

data class StatsSummaryUiState(
    val total: Int = 0,
    val average: Int = 0,
    val bestDay: Int = 0
)

/*
---------------------------------------------------
VIEWMODEL
---------------------------------------------------
*/

class StatsViewModel(
    private val waterDao: WaterDao
) : ViewModel() {

    companion object {

        private const val WEEK_MS =
            7L * 24L * 60L * 60L * 1000L
    }

    /*
    ---------------------------------------------------
    RANGE
    ---------------------------------------------------
    */

    private val _selectedRange =
        MutableStateFlow(
            StatsRange.WEEK
        )

    val selectedRange =
        _selectedRange.asStateFlow()

    fun setRange(
        range: StatsRange
    ) {

        _selectedRange.value = range
    }

    /*
    ---------------------------------------------------
    STATS
    ---------------------------------------------------
    */

    val stats: StateFlow<StatsSummaryUiState> =

        selectedRange
            .flatMapLatest { range ->

                val start = when (range) {

                    StatsRange.WEEK -> {

                        System.currentTimeMillis() -
                                WEEK_MS
                    }

                    StatsRange.MONTH -> {

                        getMonthStart()
                    }
                }

                combine(

                    waterDao.getTotalBetween(
                        start = start,

                        end =
                            System.currentTimeMillis()
                    ),

                    waterDao.getEntriesBetween(
                        start = start,

                        end =
                            System.currentTimeMillis()
                    )

                ) { total, entries ->

                    val grouped =

                        entries.groupBy { entry ->

                            val calendar =
                                Calendar.getInstance()

                            calendar.timeInMillis =
                                entry.timestamp

                            calendar.get(
                                Calendar.DAY_OF_MONTH
                            )
                        }

                    val average =

                        if (grouped.isNotEmpty()) {

                            total / grouped.size

                        } else {

                            0
                        }

                    val bestDay =

                        grouped.maxOfOrNull {
                                (_, dayEntries) ->

                            dayEntries.sumOf {
                                it.amount
                            }
                        } ?: 0

                    StatsSummaryUiState(
                        total = total,

                        average = average,

                        bestDay = bestDay
                    )
                }
            }

            .stateIn(
                scope = viewModelScope,

                started =
                    SharingStarted
                        .WhileSubscribed(5000),

                initialValue =
                    StatsSummaryUiState()
            )

    /*
    ---------------------------------------------------
    MONTH START
    ---------------------------------------------------
    */

    private fun getMonthStart(): Long {

        val calendar =
            Calendar.getInstance()

        calendar.set(
            Calendar.DAY_OF_MONTH,
            1
        )

        calendar.set(
            Calendar.HOUR_OF_DAY,
            0
        )

        calendar.set(
            Calendar.MINUTE,
            0
        )

        calendar.set(
            Calendar.SECOND,
            0
        )

        calendar.set(
            Calendar.MILLISECOND,
            0
        )

        return calendar.timeInMillis
    }
}