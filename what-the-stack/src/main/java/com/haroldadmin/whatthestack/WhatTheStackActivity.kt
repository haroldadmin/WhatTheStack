package com.haroldadmin.whatthestack

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updatePadding
import com.google.android.material.snackbar.Snackbar
import dev.chrisbanes.insetter.doOnApplyWindowInsets
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
        setContentView(R.layout.activity_what_the_stack)

        nestedScrollRoot.apply {
            systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)

            doOnApplyWindowInsets { view, insets, initialState ->
                view.updatePadding(
                        top = initialState.paddings.top + insets.systemWindowInsetTop
                )
            }
        }

        val type = intent.getStringExtra(KEY_EXCEPTION_TYPE)
        val cause = intent.getStringExtra(KEY_EXCEPTION_CAUSE)
        val message = intent.getStringExtra(KEY_EXCEPTION_MESSAGE)
        val stackTrace = intent.getStringExtra(KEY_EXCEPTION_STACKTRACE)

        stacktrace.text = stackTrace
        exceptionName.text = getString(R.string.exception_name, type)
        exceptionCause.text = getString(R.string.exception_cause, cause)
        exceptionMessage.text = getString(R.string.exception_message, message)

        copyStacktrace.setOnClickListener {
            val clipping = ClipData.newPlainText("stacktrace", stackTrace)
            clipboardManager.setPrimaryClip(clipping)
            snackbar { R.string.copied_message }
        }
    }

    private inline fun snackbar(messageProvider: () -> Int) {
        Snackbar.make(nestedScrollRoot, messageProvider(), Snackbar.LENGTH_SHORT).show()
    }
}
