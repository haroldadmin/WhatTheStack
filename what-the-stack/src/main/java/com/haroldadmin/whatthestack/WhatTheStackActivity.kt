package com.haroldadmin.whatthestack

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.google.android.material.snackbar.Snackbar
import com.haroldadmin.whatthestack.databinding.ActivityWhatTheStackBinding
import dev.chrisbanes.insetter.Insetter
import dev.chrisbanes.insetter.Side
import dev.chrisbanes.insetter.windowInsetTypesOf

/**
 * An Activity which displays various pieces of information regarding the exception which
 * occurred.
 */
class WhatTheStackActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWhatTheStackBinding

    private val clipboardManager: ClipboardManager by lazy {
        getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWhatTheStackBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        Insetter.builder()
            .padding(windowInsetTypesOf(statusBars = true, navigationBars = true))
            .applyToView(binding.root)

        val type = intent.getStringExtra(KEY_EXCEPTION_TYPE)
        val cause = intent.getStringExtra(KEY_EXCEPTION_CAUSE)
        val message = intent.getStringExtra(KEY_EXCEPTION_MESSAGE)
        val stackTrace = intent.getStringExtra(KEY_EXCEPTION_STACKTRACE)

        binding.stacktrace.apply {
            text = stackTrace
            setHorizontallyScrolling(true)
            movementMethod = ScrollingMovementMethod()
        }

        binding.exceptionName.apply {
            text = getString(R.string.exception_name, type)
        }

        binding.exceptionCause.apply {
            text = getString(R.string.exception_cause, cause)
        }

        binding.exceptionMessage.apply {
            text = getString(R.string.exception_message, message)
        }

        binding.copyStacktrace.apply {
            setOnClickListener {
                val clipping = ClipData.newPlainText("stacktrace", stackTrace)
                clipboardManager.setPrimaryClip(clipping)
                snackbar { R.string.copied_message }
            }
        }

        binding.shareStacktrace.apply {
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

        binding.launchApplication.apply {
            setOnClickListener {
                context.packageManager.getLaunchIntentForPackage(applicationContext.packageName)
                    ?.let {
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(it)
                    }
            }
        }

        binding.searchStackoverflow.apply {
            setOnClickListener {
                val searchQuery = "$cause: $message"
                val searchIntent: Intent = Intent().apply {
                    this.action = Intent.ACTION_VIEW
                    this.data = Uri.parse(generateStackoverflowSearchUrl(searchQuery))
                }
                startActivity(searchIntent)
            }
        }
    }

    private inline fun snackbar(messageProvider: () -> Int) {
        Snackbar.make(binding.nestedScrollRoot, messageProvider(), Snackbar.LENGTH_SHORT).show()
    }
}
