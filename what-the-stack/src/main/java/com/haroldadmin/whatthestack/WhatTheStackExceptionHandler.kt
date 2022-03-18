package com.haroldadmin.whatthestack

import android.os.Message
import android.os.Messenger
import android.util.Log
import androidx.core.os.bundleOf

/**
 * A [Thread.UncaughtExceptionHandler] which is meant to be used as a default exception handler on
 * the application.
 *
 * It runs in the host app's process to:
 * 1. Process any exception it catches and forward the result in a [Message] to [WhatTheStackService]
 * 2. Call the default exception handler it replaced, if any
 * 3. Kill the app process if there was no previous default exception handler
 */
@HostAppProcess
internal class WhatTheStackExceptionHandler(
    private val serviceMessenger: Messenger,
    private val defaultHandler: Thread.UncaughtExceptionHandler?,
) : Thread.UncaughtExceptionHandler {
    override fun uncaughtException(t: Thread, e: Throwable) {
        e.printStackTrace()
        val exceptionData = e.process()
        serviceMessenger.send(
            Message().apply {
                data = bundleOf(
                    KEY_EXCEPTION_TYPE to exceptionData.type,
                    KEY_EXCEPTION_CAUSE to exceptionData.cause,
                    KEY_EXCEPTION_MESSAGE to exceptionData.message,
                    KEY_EXCEPTION_STACKTRACE to exceptionData.stacktrace
                )
            }
        )

        defaultHandler?.uncaughtException(t, e)
    }
}
