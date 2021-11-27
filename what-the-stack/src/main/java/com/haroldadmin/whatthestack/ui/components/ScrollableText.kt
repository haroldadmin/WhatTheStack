package com.haroldadmin.whatthestack.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily

@Composable
internal fun ScrollableText(
    text: String,
    style: TextStyle,
    fontFamily: FontFamily,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
) {
    Text(
        text = text,
        style = style,
        fontFamily = fontFamily,
        color = color,
        modifier = modifier.horizontalScroll(rememberScrollState())
    )
}
