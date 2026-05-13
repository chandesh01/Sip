package com.sip.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material.icons.filled.Settings

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface

import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource

import com.sip.R

import com.sip.ui.theme.SipDimens
import com.sip.ui.theme.SipShapes

enum class SipScreen {

    HOME,
    HISTORY,

    STATS,
    SETTINGS
}

@Composable
fun SipBottomNavigation(
    currentScreen: SipScreen,
    onScreenSelected: (SipScreen) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()

            .padding(
                horizontal =
                    SipDimens
                        .BottomBarHorizontalPadding,

                vertical =
                    SipDimens
                        .BottomBarVerticalPadding
            ),

        contentAlignment =
            Alignment.Center
    ) {

        Surface(

            shape =
                SipShapes
                    .BottomBar,

            tonalElevation = 6.dp,

            shadowElevation = 10.dp,

            color =
                MaterialTheme
                    .colorScheme
                    .surfaceContainerHigh
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()

                    .height(
                        SipDimens
                            .BottomBarHeight
                    )

                    .padding(
                        horizontal =
                            SipDimens
                                .BottomBarContentPadding
                    ),

                horizontalArrangement =
                    Arrangement.SpaceBetween,

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                /*
                ---------------------------------------------------
                HOME
                ---------------------------------------------------
                */

                BottomNavItem(
                    selected =
                        currentScreen ==
                                SipScreen.HOME,

                    icon = {

                        Icon(
                            imageVector =
                                Icons.Default.Home,

                            contentDescription =
                                stringResource(
                                    R.string.home
                                ),

                            modifier = Modifier
                                .size(
                                    SipDimens
                                        .BottomBarIconSize
                                )
                        )
                    },

                    onClick = {

                        onScreenSelected(
                            SipScreen.HOME
                        )
                    }
                )

                /*
                ---------------------------------------------------
                HISTORY
                ---------------------------------------------------
                */

                BottomNavItem(
                    selected =
                        currentScreen ==
                                SipScreen.HISTORY,

                    icon = {

                        Icon(
                            imageVector =
                                Icons.Default.History,

                            contentDescription =
                                stringResource(
                                    R.string.history
                                ),

                            modifier = Modifier
                                .size(
                                    SipDimens
                                        .BottomBarIconSize
                                )
                        )
                    },

                    onClick = {

                        onScreenSelected(
                            SipScreen.HISTORY
                        )
                    }
                )

                /*
                ---------------------------------------------------
                STATS
                ---------------------------------------------------
                */

                BottomNavItem(
                    selected =
                        currentScreen ==
                                SipScreen.STATS,

                    icon = {

                        Icon(
                            imageVector =
                                Icons.Default.Insights,

                            contentDescription =
                                "Stats",

                            modifier = Modifier
                                .size(
                                    SipDimens
                                        .BottomBarIconSize
                                )
                        )
                    },

                    onClick = {

                        onScreenSelected(
                            SipScreen.STATS
                        )
                    }
                )

                /*
                ---------------------------------------------------
                SETTINGS
                ---------------------------------------------------
                */

                BottomNavItem(
                    selected =
                        currentScreen ==
                                SipScreen.SETTINGS,

                    icon = {

                        Icon(
                            imageVector =
                                Icons.Default.Settings,

                            contentDescription =
                                stringResource(
                                    R.string.settings
                                ),

                            modifier = Modifier
                                .size(
                                    SipDimens
                                        .BottomBarIconSize
                                )
                        )
                    },

                    onClick = {

                        onScreenSelected(
                            SipScreen.SETTINGS
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun BottomNavItem(
    selected: Boolean,
    icon: @Composable () -> Unit,
    onClick: () -> Unit
) {

    Box(
        modifier = Modifier

            .clip(CircleShape)

            .background(

                if (selected) {

                    MaterialTheme
                        .colorScheme
                        .secondaryContainer

                } else {

                    Color.Transparent
                }
            )

            .clickable {

                onClick()
            }

            .padding(
                horizontal =
                    SipDimens
                        .BottomBarItemHorizontalPadding,

                vertical =
                    SipDimens
                        .BottomBarItemVerticalPadding
            ),

        contentAlignment =
            Alignment.Center
    ) {

        icon()
    }
}