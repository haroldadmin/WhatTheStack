package com.haroldadmin.whatthestack.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * A text label with the "overline" typography style
 */
@Composable
internal fun OverlineLabel(label: String, modifier: Modifier = Modifier) {
    Text(
        text = label,
        style = MaterialTheme.typography.overline,
        color = if (isSystemInDarkTheme()) MaterialTheme.colors.primary else MaterialTheme.colors.primaryVariant,
        modifier = modifier
    )
}