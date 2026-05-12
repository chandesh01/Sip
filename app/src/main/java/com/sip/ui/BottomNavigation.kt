package com.sip.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sip.R

enum class SipScreen {

    HOME,
    HISTORY,
    SETTINGS
}

@Composable
fun SipBottomNavigation(
    currentScreen: SipScreen,
    onScreenSelected: (SipScreen) -> Unit
) {

    NavigationBar(

        tonalElevation = 0.dp,

        containerColor =
            MaterialTheme
                .colorScheme
                .surfaceContainer
    ) {

        NavigationBarItem(
            selected = currentScreen == SipScreen.HOME,

            onClick = {
                onScreenSelected(SipScreen.HOME)
            },

            alwaysShowLabel = false,

            label = {},

            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription =
                        stringResource(R.string.home)
                )
            }
        )

        NavigationBarItem(
            selected = currentScreen == SipScreen.HISTORY,

            onClick = {
                onScreenSelected(SipScreen.HISTORY)
            },

            alwaysShowLabel = false,

            label = {},

            icon = {
                Icon(
                    imageVector = Icons.Default.History,
                    contentDescription =
                        stringResource(R.string.history)
                )
            }
        )

        NavigationBarItem(
            selected = currentScreen == SipScreen.SETTINGS,

            onClick = {
                onScreenSelected(SipScreen.SETTINGS)
            },

            alwaysShowLabel = false,

            label = {},

            icon = {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription =
                        stringResource(R.string.settings)
                )
            }
        )
    }
}