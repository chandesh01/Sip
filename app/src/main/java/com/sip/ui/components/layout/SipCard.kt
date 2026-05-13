package com.sip.ui.components.cards

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Card

import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier

import com.sip.ui.theme.SipDimens
import com.sip.ui.theme.SipShapes

@Composable
fun SipCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {

    Card(
        modifier = modifier
            .fillMaxWidth(),

        shape = SipShapes.LargeCard
    ) {

        Column(
            modifier = Modifier
                .padding(
                    SipDimens.CardPadding
                ),

            content = content
        )
    }
}