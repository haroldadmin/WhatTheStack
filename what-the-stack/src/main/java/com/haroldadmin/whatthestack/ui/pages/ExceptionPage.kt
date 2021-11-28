package com.haroldadmin.whatthestack.ui.pages

import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.net.Uri
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.statusBarsHeight
import com.haroldadmin.whatthestack.R
import com.haroldadmin.whatthestack.generateStackoverflowSearchUrl
import com.haroldadmin.whatthestack.ui.components.OutlinedIconButton
import com.haroldadmin.whatthestack.ui.components.OverlineLabel
import com.haroldadmin.whatthestack.ui.preview.SampleData
import com.haroldadmin.whatthestack.ui.theme.WhatTheStackTheme
import kotlinx.coroutines.launch

@Composable
fun ExceptionPage(
    type: String,
    message: String,
    stackTrace: String
) {
    val clipboard = LocalClipboardManager.current
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val snackbarMessage = stringResource(id = R.string.copied_message)

    Scaffold(scaffoldState = scaffoldState) {
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.statusBarsHeight(additional = 8.dp))
            PageHeader()
            ExceptionDetails(
                type = type,
                message = message,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            ExceptionOptions(
                onCopy = {
                    coroutineScope.launch {
                        clipboard.setText(AnnotatedString(stackTrace))
                        scaffoldState.snackbarHostState.showSnackbar(snackbarMessage)
                    }
                },
                onShare = {
                    val sendIntent: Intent = Intent().apply {
                        this.action = Intent.ACTION_SEND
                        this.putExtra(Intent.EXTRA_TEXT, stackTrace)
                        this.type = "text/plain"
                    }

                    val shareIntent = Intent.createChooser(sendIntent, "Stacktrace")
                    context.startActivity(shareIntent)
                },
                onSearch = {
                    val searchQuery = "$type: $message"
                    val url = generateStackoverflowSearchUrl(searchQuery)
                    val searchIntent = Intent().apply {
                        action = Intent.ACTION_VIEW
                        data = Uri.parse(url)
                    }
                    context.startActivity(searchIntent)
                },
                onRestart = {
                    val applicationContext = context.applicationContext
                    val packageManager = applicationContext.packageManager
                    val packageName = applicationContext.packageName

                    val launchIntent = packageManager.getLaunchIntentForPackage(packageName)
                    if (launchIntent != null) {
                        launchIntent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        context.startActivity(launchIntent)
                    }
                }
            )
            Stacktrace(
                stackTrace = stackTrace,
                modifier = Modifier.padding(top = 8.dp)
            )
            Spacer(modifier = Modifier.navigationBarsHeight(additional = 8.dp))
        }
    }
}

@Composable
fun PageHeader() {
    Text(
        stringResource(id = R.string.header_text),
        style = MaterialTheme.typography.h4,
        modifier = Modifier.padding(vertical = 4.dp),
        color = MaterialTheme.colors.onBackground
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
        text = stringResource(id = R.string.explanation_text),
        color = MaterialTheme.colors.onBackground
    )
}

@Composable
fun ExceptionDetails(type: String, message: String, modifier: Modifier) {
    Column(modifier = modifier) {
        OverlineLabel(label = stringResource(id = R.string.exception_name))
        Text(
            text = type,
            fontFamily = FontFamily.Monospace,
            color = MaterialTheme.colors.onBackground
        )
        Spacer(modifier = Modifier.height(8.dp))
        OverlineLabel(label = stringResource(id = R.string.exception_message))
        Text(
            text = message,
            fontFamily = FontFamily.Monospace,
            color = MaterialTheme.colors.onBackground
        )
    }
}

@Composable
fun ExceptionOptions(
    onCopy: () -> Unit,
    onShare: () -> Unit,
    onRestart: () -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        OutlinedIconButton(
            text = stringResource(id = R.string.copy_stacktrace),
            iconId = R.drawable.ic_outline_content_copy_24,
            onClick = onCopy,
            contentDescription = "Copy",
            modifier = Modifier.padding(vertical = 4.dp),
        )
        OutlinedIconButton(
            text = stringResource(id = R.string.share_stacktrace),
            iconId = R.drawable.ic_outline_share_24,
            onClick = onShare,
            contentDescription = "Share",
            modifier = Modifier.padding(vertical = 4.dp)
        )
        OutlinedIconButton(
            text = stringResource(id = R.string.search_stackoverflow),
            iconId = R.drawable.ic_round_search_24,
            onClick = onSearch,
            contentDescription = "Search Stackoverflow",
            modifier = Modifier.padding(vertical = 4.dp)
        )
        OutlinedIconButton(
            text = stringResource(id = R.string.restart_application),
            iconId = R.drawable.ic_baseline_refresh_24,
            onClick = onRestart,
            contentDescription = "Restart"
        )
    }
}

@Composable
fun Stacktrace(stackTrace: String, modifier: Modifier) {
    Column(modifier) {
        OverlineLabel(label = stringResource(id = R.string.stacktrace))
        Surface(
            color = Color.LightGray.copy(alpha = 0.5f),
            modifier = Modifier.padding(top = 4.dp)
        ) {
            SelectionContainer {
                Text(
                    text = stackTrace,
                    style = MaterialTheme.typography.body2.copy(fontSize = 12.sp),
                    fontFamily = FontFamily.Monospace,
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier
                        .padding(4.dp)
                        .horizontalScroll(rememberScrollState())
                )
            }
        }
    }
}

@Preview
@Composable
fun ExceptionPagePreview() {
    WhatTheStackTheme {
        ExceptionPage(
            type = SampleData.ExceptionType,
            message = SampleData.ExceptionMessage,
            stackTrace = SampleData.Stacktrace
        )
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ExceptionPagePreviewNightMode() {
    WhatTheStackTheme {
        ExceptionPage(
            type = SampleData.ExceptionType,
            message = SampleData.ExceptionMessage,
            stackTrace = SampleData.Stacktrace
        )
    }
}
