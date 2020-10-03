package com.haroldadmin.whatthestack

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Icon
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.ScrollableRow
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import java.util.*
import kotlinx.coroutines.launch

/**
 * An Activity which displays various pieces of information regarding the exception which
 * occurred.
 */

class WhatTheStackActivity : AppCompatActivity() {

    private val clipboardManager: ClipboardManager by lazy {
        getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    }

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, true)

        val type = intent.getStringExtra(KEY_EXCEPTION_TYPE)
        val cause = intent.getStringExtra(KEY_EXCEPTION_CAUSE)
        val message = intent.getStringExtra(KEY_EXCEPTION_MESSAGE)
        val stackTrace = intent.getStringExtra(KEY_EXCEPTION_STACKTRACE) ?: ""

        setContent {
            val state = rememberScaffoldState()
            Scaffold(scaffoldState = state) {
                ScrollableColumn {
                    Header()
                    HeaderSubtitle()
                    ExceptionName(type)
                    ExceptionCause(cause)
                    ExceptionMessage(message)
                    CopyStackTraceButton(stackTrace, state.snackbarHostState)
                    ShareStackTraceButton(stackTrace)
                    LaunchAppButton()
                    StackTraceHeader()
                    StackTrace(stackTrace)
                }
            }
        }
    }

    //region Header
    @Composable
    fun Header(stackTrace: String = getString(R.string.header_text)) =
        Text(
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp),
            text = stackTrace,
            style = typography.h4,
            color = colors.onSurface
        )

    @Composable
    fun HeaderSubtitle() =
        Text(
            modifier = Modifier.fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            text = getString(R.string.explanation_text),
            style = typography.body1,
            color = colors.onSurface
        )
    //endregion

    //region Exception Texts
    @Composable
    fun ExceptionName(type: String?) =
        ExceptionText(R.string.exception_name, type)

    @Composable
    fun ExceptionCause(cause: String?) =
        ExceptionText(R.string.exception_cause, cause)

    @Composable
    fun ExceptionMessage(message: String?) =
        ExceptionText(R.string.exception_message, message)

    @Composable
    fun ExceptionText(@StringRes stringResId: Int, arg: String?) {
        if (!arg.isNullOrEmpty()) {
            Text(
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 16.dp, top = 4.dp, end = 16.dp),
                color = typography.body1.color.copy(0.7f),
                fontFamily = FontFamily.Monospace,
                text = stringResource(stringResId, arg),
                style = typography.body1
            )
        }
    }
    //endregion

    //region Buttons
    @ExperimentalMaterialApi
    @Composable
    fun CopyStackTraceButton(stackTrace: String, host: SnackbarHostState) {
        val snackMessage = stringResource(R.string.copied_message)

        ImageButton(
            stringResource(R.string.copy_stacktrace),
            R.drawable.ic_outline_content_copy_24
        ) {
            val clipping = ClipData.newPlainText("stacktrace", stackTrace)
            clipboardManager.setPrimaryClip(clipping)

            lifecycleScope.launch {
                host.showSnackbar(snackMessage)
            }
        }
    }

    @Composable
    fun ShareStackTraceButton(stackTrace: String) =
        ImageButton(
            stringResource(R.string.share_stacktrace),
            R.drawable.ic_outline_share_24
        ) {
            val sendIntent = Intent().apply {
                this.action = Intent.ACTION_SEND
                this.putExtra(Intent.EXTRA_TEXT, stackTrace)
                this.type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

    @Composable
    fun LaunchAppButton() =
        ImageButton(
            stringResource(R.string.restart_application),
            R.drawable.ic_baseline_refresh_24
        ) {
            packageManager.getLaunchIntentForPackage(applicationContext.packageName)?.let {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }

    @Composable
    fun ImageButton(textString: String, @DrawableRes iconResId: Int, doOnClick: () -> Unit) =
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 2.dp),
            onClick = doOnClick
        ) {
            Icon(
                asset = vectorResource(id = iconResId),
                tint = colorResource(id = R.color.secondaryColor)
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = textString.toUpperCase(Locale.getDefault()),
                color = colors.onSurface
            )
        }
    //endregion

    //region StackTrace
    @Composable
    fun StackTraceHeader() =
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, start = 16.dp, end = 16.dp),
            text = stringResource(R.string.stacktrace).toUpperCase(Locale.getDefault()),
            style = typography.overline
        )

    @Composable
    fun StackTrace(stackTraceText: String) =
        ScrollableRow(
            Modifier
                .fillMaxHeight()
                .padding(top = 4.dp, start = 16.dp, end = 16.dp)
        ) {
            Text(
                text = stackTraceText,
                style = typography.body2,
                color = colors.error,
                fontFamily = FontFamily.Monospace
            )
        }
    //endregion
}
