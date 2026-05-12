package com.sip.data.settings

import android.content.Context

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

import androidx.datastore.preferences.preferencesDataStore

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(
    name = "sip_settings"
)

class SettingsPreferences(
    private val context: Context
) {

    companion object {

        /*
        ---------------------------------------------------
        REMINDERS
        ---------------------------------------------------
        */

        private val REMINDERS_ENABLED =
            booleanPreferencesKey(
                "reminders_enabled"
            )

        /*
        ---------------------------------------------------
        INTERVAL
        ---------------------------------------------------
        */

        private val INTERVAL_MINUTES =
            longPreferencesKey(
                "interval_minutes"
            )

        /*
        ---------------------------------------------------
        START TIME
        ---------------------------------------------------
        */

        private val START_TIME =
            stringPreferencesKey(
                "start_time"
            )

        /*
        ---------------------------------------------------
        END TIME
        ---------------------------------------------------
        */

        private val END_TIME =
            stringPreferencesKey(
                "end_time"
            )

        /*
        ---------------------------------------------------
        DAILY GOAL
        ---------------------------------------------------
        */

        private val DAILY_GOAL =
            longPreferencesKey(
                "daily_goal"
            )
    }

    /*
    ---------------------------------------------------
    REMINDERS ENABLED
    ---------------------------------------------------
    */

    val remindersEnabled: Flow<Boolean> =
        context.dataStore.data.map {

            it[REMINDERS_ENABLED] ?: false
        }

    /*
    ---------------------------------------------------
    INTERVAL
    ---------------------------------------------------
    */

    val intervalMinutes: Flow<Long> =
        context.dataStore.data.map {

            it[INTERVAL_MINUTES] ?: 60L
        }

    /*
    ---------------------------------------------------
    START TIME
    ---------------------------------------------------
    */

    val startTime: Flow<String> =
        context.dataStore.data.map {

            it[START_TIME] ?: "08:00"
        }

    /*
    ---------------------------------------------------
    END TIME
    ---------------------------------------------------
    */

    val endTime: Flow<String> =
        context.dataStore.data.map {

            it[END_TIME] ?: "22:00"
        }

    /*
    ---------------------------------------------------
    DAILY GOAL
    ---------------------------------------------------
    */

    val dailyGoal: Flow<Long> =
        context.dataStore.data.map {

            it[DAILY_GOAL] ?: 4000L
        }

    /*
    ---------------------------------------------------
    SET REMINDERS ENABLED
    ---------------------------------------------------
    */

    suspend fun setRemindersEnabled(
        enabled: Boolean
    ) {

        context.dataStore.edit {

            it[REMINDERS_ENABLED] = enabled
        }
    }

    /*
    ---------------------------------------------------
    SET INTERVAL
    ---------------------------------------------------
    */

    suspend fun setIntervalMinutes(
        minutes: Long
    ) {

        context.dataStore.edit {

            it[INTERVAL_MINUTES] = minutes
        }
    }

    /*
    ---------------------------------------------------
    SET START TIME
    ---------------------------------------------------
    */

    suspend fun setStartTime(
        time: String
    ) {

        context.dataStore.edit {

            it[START_TIME] = time
        }
    }

    /*
    ---------------------------------------------------
    SET END TIME
    ---------------------------------------------------
    */

    suspend fun setEndTime(
        time: String
    ) {

        context.dataStore.edit {

            it[END_TIME] = time
        }
    }

    /*
    ---------------------------------------------------
    SET DAILY GOAL
    ---------------------------------------------------
    */

    suspend fun setDailyGoal(
        goal: Long
    ) {

        context.dataStore.edit {

            it[DAILY_GOAL] = goal
        }
    }
}