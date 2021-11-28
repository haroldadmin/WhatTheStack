package com.haroldadmin.whatthestack.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Color(0xffd32f2f),
    primaryVariant = Color(0xffff6659),
    secondary = Color(0xff616161),
    secondaryVariant = Color(0xff373737),
)

private val LightColorPalette = lightColors(
    primary = Color(0xffd32f2f),
    primaryVariant = Color(0xff9a0007),
    secondary = Color(0xff616161),
    secondaryVariant = Color(0x33373737),
)

internal val SystemBarsColor = Color(0x33373737)

@Composable
fun WhatTheStackTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(colors = colors, content = content)
}
