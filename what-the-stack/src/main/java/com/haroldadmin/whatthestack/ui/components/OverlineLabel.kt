package com.haroldadmin.whatthestack.ui.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

/**
 * A text label with the "overline" typography style
 */
@Composable
internal fun OverlineLabel(label: String, modifier: Modifier = Modifier) {
    Text(
        text = label,
        style = MaterialTheme.typography.overline,
        fontWeight = FontWeight.Medium,
        color = MaterialTheme.colors.onSurface,
        modifier = modifier
    )
}
