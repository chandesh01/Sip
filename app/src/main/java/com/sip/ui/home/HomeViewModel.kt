package com.sip.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.sip.data.WaterDao
import com.sip.data.WaterEntry

import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

import kotlinx.coroutines.launch

import java.util.Calendar

class HomeViewModel(
    private val waterDao: WaterDao
) : ViewModel() {

    companion object {

        private const val DAY_MS =
            24 * 60 * 60 * 1000L
    }

    /*
    ---------------------------------------------------
    DATE RANGES
    ---------------------------------------------------
    */

    private fun startOfDay(): Long {

        return Calendar.getInstance().apply {

            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

        }.timeInMillis
    }

    private fun endOfDay(): Long {

        return Calendar.getInstance().apply {

            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)

        }.timeInMillis
    }

    private fun startOfWeek(): Long {

        return Calendar.getInstance().apply {

            while (
                get(Calendar.DAY_OF_WEEK)
                != Calendar.MONDAY
            ) {
                add(Calendar.DAY_OF_MONTH, -1)
            }

            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

        }.timeInMillis
    }

    private fun startOfMonth(): Long {

        return Calendar.getInstance().apply {

            set(
                Calendar.DAY_OF_MONTH,
                1
            )

            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

        }.timeInMillis
    }

    /*
    ---------------------------------------------------
    ELAPSED DAYS
    ---------------------------------------------------
    */

    private fun elapsedWeekDays(): Int {

        val now =
            Calendar.getInstance()

        val start =
            Calendar.getInstance().apply {

                while (
                    get(Calendar.DAY_OF_WEEK)
                    != Calendar.MONDAY
                ) {
                    add(Calendar.DAY_OF_MONTH, -1)
                }

                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

        val diff =
            now.timeInMillis -
                    start.timeInMillis

        return (
                (diff / DAY_MS) + 1
                ).toInt()
    }

    private fun elapsedMonthDays(): Int {

        return Calendar
            .getInstance()
            .get(
                Calendar.DAY_OF_MONTH
            )
    }

    /*
    ---------------------------------------------------
    TOTALS
    ---------------------------------------------------
    */

    val todayTotal: StateFlow<Int> =
        waterDao
            .getTotalBetween(
                start = startOfDay(),
                end = endOfDay()
            )
            .stateIn(
                scope = viewModelScope,

                started =
                    SharingStarted
                        .WhileSubscribed(5000),

                initialValue = 0
            )

    val weekTotal: StateFlow<Int> =
        waterDao
            .getTotalBetween(
                start = startOfWeek(),
                end = Long.MAX_VALUE
            )
            .stateIn(
                scope = viewModelScope,

                started =
                    SharingStarted
                        .WhileSubscribed(5000),

                initialValue = 0
            )

    val monthTotal: StateFlow<Int> =
        waterDao
            .getTotalBetween(
                start = startOfMonth(),
                end = Long.MAX_VALUE
            )
            .stateIn(
                scope = viewModelScope,

                started =
                    SharingStarted
                        .WhileSubscribed(5000),

                initialValue = 0
            )

    /*
    ---------------------------------------------------
    AVERAGES
    ---------------------------------------------------
    */

    val weekAverage: StateFlow<Float> =
        weekTotal
            .map { total ->

                total.toFloat() /
                        elapsedWeekDays()
            }
            .stateIn(
                scope = viewModelScope,

                started =
                    SharingStarted
                        .WhileSubscribed(5000),

                initialValue = 0f
            )

    val monthAverage: StateFlow<Float> =
        monthTotal
            .map { total ->

                total.toFloat() /
                        elapsedMonthDays()
            }
            .stateIn(
                scope = viewModelScope,

                started =
                    SharingStarted
                        .WhileSubscribed(5000),

                initialValue = 0f
            )

    /*
    ---------------------------------------------------
    ADD WATER
    ---------------------------------------------------
    */

    fun addWater(
        amount: Int
    ) {

        viewModelScope.launch {

            waterDao.insertEntry(
                WaterEntry(
                    amount = amount,

                    timestamp =
                        System.currentTimeMillis()
                )
            )
        }
    }

    /*
    ---------------------------------------------------
    DELETE WATER
    ---------------------------------------------------
    */

    fun deleteEntry(
        entry: WaterEntry
    ) {

        viewModelScope.launch {

            waterDao.deleteEntry(entry)
        }
    }
}