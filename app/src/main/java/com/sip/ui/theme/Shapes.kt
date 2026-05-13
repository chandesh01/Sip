package com.sip.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

object SipShapes {

    val LargeCard =
        RoundedCornerShape(
            SipDimens.LargeCardRadius
        )

    val MediumCard =
        RoundedCornerShape(
            SipDimens.MediumCardRadius
        )

    val Pill =
        RoundedCornerShape(
            SipDimens.PillRadius
        )
    val BottomBar =
        RoundedCornerShape(30.dp)
}