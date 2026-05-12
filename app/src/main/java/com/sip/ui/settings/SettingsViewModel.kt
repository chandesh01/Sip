package com.sip.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.sip.data.settings.SettingsPreferences

import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

import kotlinx.coroutines.launch

class SettingsViewModel(
    private val preferences: SettingsPreferences
) : ViewModel() {

    /*
    ---------------------------------------------------
    REMINDERS ENABLED
    ---------------------------------------------------
    */

    val remindersEnabled =
        preferences
            .remindersEnabled
            .stateIn(
                scope = viewModelScope,

                started =
                    SharingStarted
                        .WhileSubscribed(5000),

                initialValue = false
            )

    /*
    ---------------------------------------------------
    INTERVAL
    ---------------------------------------------------
    */

    val intervalMinutes =
        preferences
            .intervalMinutes
            .stateIn(
                scope = viewModelScope,

                started =
                    SharingStarted
                        .WhileSubscribed(5000),

                initialValue = 60L
            )

    /*
    ---------------------------------------------------
    START TIME
    ---------------------------------------------------
    */

    val startTime =
        preferences
            .startTime
            .stateIn(
                scope = viewModelScope,

                started =
                    SharingStarted
                        .WhileSubscribed(5000),

                initialValue = "08:00"
            )

    /*
    ---------------------------------------------------
    END TIME
    ---------------------------------------------------
    */

    val endTime =
        preferences
            .endTime
            .stateIn(
                scope = viewModelScope,

                started =
                    SharingStarted
                        .WhileSubscribed(5000),

                initialValue = "22:00"
            )

    /*
    ---------------------------------------------------
    DAILY GOAL
    ---------------------------------------------------
    */

    val dailyGoal =
        preferences
            .dailyGoal
            .stateIn(
                scope = viewModelScope,

                started =
                    SharingStarted
                        .WhileSubscribed(5000),

                initialValue = 4000L
            )

    /*
    ---------------------------------------------------
    REMINDERS ENABLED
    ---------------------------------------------------
    */

    fun setRemindersEnabled(
        enabled: Boolean
    ) {

        viewModelScope.launch {

            preferences
                .setRemindersEnabled(
                    enabled
                )
        }
    }

    /*
    ---------------------------------------------------
    INTERVAL
    ---------------------------------------------------
    */

    fun setIntervalMinutes(
        minutes: Long
    ) {

        viewModelScope.launch {

            preferences
                .setIntervalMinutes(
                    minutes
                )
        }
    }

    /*
    ---------------------------------------------------
    START TIME
    ---------------------------------------------------
    */

    fun setStartTime(
        time: String
    ) {

        viewModelScope.launch {

            preferences
                .setStartTime(time)
        }
    }

    /*
    ---------------------------------------------------
    END TIME
    ---------------------------------------------------
    */

    fun setEndTime(
        time: String
    ) {

        viewModelScope.launch {

            preferences
                .setEndTime(time)
        }
    }

    /*
    ---------------------------------------------------
    DAILY GOAL
    ---------------------------------------------------
    */

    fun setDailyGoal(
        goal: Long
    ) {

        viewModelScope.launch {

            preferences
                .setDailyGoal(goal)
        }
    }
}
