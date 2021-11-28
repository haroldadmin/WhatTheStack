package com.haroldadmin.crashyapp.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val ColorPalette = lightColors(
    primary = Color(0xffd32f2f),
    primaryVariant = Color(0xff9a0007),
    secondary = Color(0xff616161),
    secondaryVariant = Color(0x33373737),
)

@Composable
fun CrashyAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(colors = ColorPalette, content = content)
}
