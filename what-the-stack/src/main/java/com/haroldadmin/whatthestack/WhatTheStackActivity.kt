package com.haroldadmin.whatthestack

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.haroldadmin.whatthestack.databinding.ActivityWhatTheStackBinding
import dev.chrisbanes.insetter.Insetter
import dev.chrisbanes.insetter.Side
import kotlinx.android.synthetic.main.activity_what_the_stack.*

/**
 * An Activity which displays various pieces of information regarding the exception which
 * occurred.
 */
class WhatTheStackActivity : AppCompatActivity() {

    private val clipboardManager: ClipboardManager by lazy {
        getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityWhatTheStackBinding = ActivityWhatTheStackBinding.inflate(layoutInflater)
        setContentView(activityWhatTheStackBinding.root)

        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_STABLE

        Insetter.builder()
            .applySystemWindowInsetsToPadding(Side.LEFT or Side.RIGHT or Side.TOP)
            .applyToView(findViewById(R.id.nestedScrollRoot))

        val type = intent.getStringExtra(KEY_EXCEPTION_TYPE)
        val cause = intent.getStringExtra(KEY_EXCEPTION_CAUSE)
        val message = intent.getStringExtra(KEY_EXCEPTION_MESSAGE)
        val stackTrace = intent.getStringExtra(KEY_EXCEPTION_STACKTRACE)

        activityWhatTheStackBinding.stacktrace.apply {
            text = stackTrace
            setHorizontallyScrolling(true)
            movementMethod = ScrollingMovementMethod()
        }

        activityWhatTheStackBinding.exceptionName.apply {
            text = getString(R.string.exception_name, type)
        }

        activityWhatTheStackBinding.exceptionCause.apply {
            text = getString(R.string.exception_cause, cause)
        }

        activityWhatTheStackBinding.exceptionMessage.apply {
            text = getString(R.string.exception_message, message)
        }

        activityWhatTheStackBinding.copyStacktrace.apply {
            setOnClickListener {
                val clipping = ClipData.newPlainText("stacktrace", stackTrace)
                clipboardManager.setPrimaryClip(clipping)
                snackbar { R.string.copied_message }
            }
        }

        activityWhatTheStackBinding.shareStacktrace.apply {
            setOnClickListener {
                val sendIntent: Intent = Intent().apply {
                    this.action = Intent.ACTION_SEND
                    this.putExtra(Intent.EXTRA_TEXT, stackTrace)
                    this.type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }
        }

        activityWhatTheStackBinding.launchApplication.apply {
            setOnClickListener {
                context.packageManager.getLaunchIntentForPackage(applicationContext.packageName)?.let {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        }
    }

    private inline fun snackbar(messageProvider: () -> Int) {
        Snackbar.make(nestedScrollRoot, messageProvider(), Snackbar.LENGTH_SHORT).show()
    }
}
