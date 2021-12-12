package com.haroldadmin.crashyapp.ui.pages

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun HomePage() {
    Scaffold(
        topBar = {
            TopAppBar {
                Text(text = "Crashy App", style = MaterialTheme.typography.h6)
            }
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Text(
                text = "Press the button to see the error screen from WhatTheStack!",
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { throw BecauseICanException() }) {
                Text(text = "Crash!")
            }
        }
    }
}

private class BecauseICanException :
    Exception("This exception is thrown purely because it can be thrown")
