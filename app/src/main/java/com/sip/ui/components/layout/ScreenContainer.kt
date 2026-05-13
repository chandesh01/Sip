package com.sip.ui.components.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope

import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier

import com.sip.ui.theme.SipDimens

@Composable
fun ScreenContainer(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,

    verticalArrangement:
    Arrangement.Vertical =

        Arrangement.spacedBy(
            SipDimens.SectionSpacing
        ),

    content: LazyListScope.() -> Unit
) {

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(
                horizontal =
                    SipDimens
                        .ScreenHorizontalPadding
            ),

        verticalArrangement =
            verticalArrangement,

        content = content
    )
}