package com.sip

import android.Manifest
import android.app.AlarmManager
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Modifier

import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel

import com.sip.data.settings.SettingsPreferences

import com.sip.ui.SipBottomNavigation
import com.sip.ui.SipScreen

import com.sip.ui.history.HistoryScreen

import com.sip.ui.home.HomeScreen
import com.sip.ui.home.HomeViewModel

import com.sip.ui.settings.SettingsScreen
import com.sip.ui.settings.SettingsViewModel

import com.sip.ui.stats.StatsScreen
import com.sip.ui.stats.StatsViewModel

import com.sip.ui.theme.SipTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(savedInstanceState)

        requestNotificationPermission()

        setContent {

            SipTheme {

                val app = application as App

                /*
                ---------------------------------------------------
                HOME VIEWMODEL
                ---------------------------------------------------
                */

                val homeViewModel:
                        HomeViewModel = viewModel(

                    factory =
                        object :
                            ViewModelProvider.Factory {

                            @Suppress(
                                "UNCHECKED_CAST"
                            )

                            override fun
                                    <T : ViewModel>
                                    create(
                                modelClass:
                                Class<T>
                            ): T {

                                return HomeViewModel(
                                    waterDao =
                                        app.database
                                            .waterDao()
                                ) as T
                            }
                        }
                )

                /*
                ---------------------------------------------------
                SETTINGS VIEWMODEL
                ---------------------------------------------------
                */

                val settingsViewModel:
                        SettingsViewModel =
                    viewModel(

                        factory =
                            object :
                                ViewModelProvider
                                .Factory {

                                @Suppress(
                                    "UNCHECKED_CAST"
                                )

                                override fun
                                        <T : ViewModel>
                                        create(
                                    modelClass:
                                    Class<T>
                                ): T {

                                    return SettingsViewModel(
                                        preferences =
                                            SettingsPreferences(
                                                this@MainActivity
                                            )
                                    ) as T
                                }
                            }
                    )

                /*
                ---------------------------------------------------
                STATS VIEWMODEL
                ---------------------------------------------------
                */

                val statsViewModel:
                        StatsViewModel =
                    viewModel(

                        factory =
                            object :
                                ViewModelProvider
                                .Factory {

                                @Suppress(
                                    "UNCHECKED_CAST"
                                )

                                override fun
                                        <T : ViewModel>
                                        create(
                                    modelClass:
                                    Class<T>
                                ): T {

                                    return StatsViewModel(
                                        waterDao =
                                            app.database
                                                .waterDao()
                                    ) as T
                                }
                            }
                    )

                /*
                ---------------------------------------------------
                DAILY GOAL
                ---------------------------------------------------
                */

                val dailyGoal by
                settingsViewModel
                    .dailyGoal
                    .collectAsState()

                /*
                ---------------------------------------------------
                NAVIGATION
                ---------------------------------------------------
                */

                var currentScreen by remember {

                    mutableStateOf(
                        SipScreen.HOME
                    )
                }

                /*
                ---------------------------------------------------
                UI
                ---------------------------------------------------
                */

                Scaffold(

                    bottomBar = {

                        SipBottomNavigation(
                            currentScreen =
                                currentScreen,

                            onScreenSelected = {
                                currentScreen = it
                            }
                        )
                    }

                ) { paddingValues ->

                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {

                        when (currentScreen) {

                            /*
                            ---------------------------------------------------
                            HOME
                            ---------------------------------------------------
                            */

                            SipScreen.HOME -> {

                                HomeScreen(
                                    paddingValues =
                                        paddingValues,

                                    viewModel =
                                        homeViewModel,

                                    dailyGoal =
                                        dailyGoal
                                )
                            }

                            /*
                            ---------------------------------------------------
                            HISTORY
                            ---------------------------------------------------
                            */

                            SipScreen.HISTORY -> {

                                val historyEntries by
                                app.database
                                    .waterDao()
                                    .getAllEntries()
                                    .collectAsState(
                                        initial = emptyList()
                                    )

                                HistoryScreen(
                                    paddingValues =
                                        paddingValues,

                                    entries =
                                        historyEntries,

                                    onDeleteEntry = {
                                        homeViewModel
                                            .deleteEntry(it)
                                    }
                                )
                            }

                            /*
                            ---------------------------------------------------
                            STATS
                            ---------------------------------------------------
                            */

                            SipScreen.STATS -> {

                                StatsScreen(
                                    paddingValues =
                                        paddingValues,

                                    viewModel =
                                        statsViewModel
                                )
                            }

                            /*
                            ---------------------------------------------------
                            SETTINGS
                            ---------------------------------------------------
                            */

                            SipScreen.SETTINGS -> {

                                SettingsScreen(
                                    paddingValues =
                                        paddingValues,

                                    viewModel =
                                        settingsViewModel
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    /*
    ---------------------------------------------------
    NOTIFICATION PERMISSION
    ---------------------------------------------------
    */

    private fun requestNotificationPermission() {

        if (
            Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.TIRAMISU
        ) {

            if (
                ContextCompat
                    .checkSelfPermission(
                        this,
                        Manifest.permission
                            .POST_NOTIFICATIONS
                    ) !=
                PackageManager.PERMISSION_GRANTED
            ) {

                ActivityCompat
                    .requestPermissions(

                        this,

                        arrayOf(
                            Manifest.permission
                                .POST_NOTIFICATIONS
                        ),

                        100
                    )
            }
        }
    }

    /*
    ---------------------------------------------------
    EXACT ALARM PERMISSION
    ---------------------------------------------------
    */

    fun requestExactAlarmPermission() {

        if (
            Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.S
        ) {

            val alarmManager =
                getSystemService(
                    AlarmManager::class.java
                )

            if (
                !alarmManager
                    .canScheduleExactAlarms()
            ) {

                val intent = Intent(
                    Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
                )

                startActivity(intent)
            }
        }
    }

    /*
    ---------------------------------------------------
    BATTERY OPTIMIZATION
    ---------------------------------------------------
    */

    fun requestBatteryOptimizationDisable() {

        val powerManager =
            getSystemService(
                PowerManager::class.java
            )

        if (
            !powerManager
                .isIgnoringBatteryOptimizations(
                    packageName
                )
        ) {

            val intent = Intent(
                Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,

                Uri.parse(
                    "package:$packageName"
                )
            )

            startActivity(intent)
        }
    }
}