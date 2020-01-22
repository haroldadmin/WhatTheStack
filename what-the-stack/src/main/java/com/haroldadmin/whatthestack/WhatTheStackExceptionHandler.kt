package com.haroldadmin.whatthestack

import android.os.Message
import android.os.Messenger
import android.os.Process
import androidx.core.os.bundleOf

/**
 * A [Thread.UncaughtExceptionHandler] which is meant to be used as a default exception handler on
 * the application. Any uncaught exceptions which are handled by this handler are processed and
 * send to [WhatTheStackService] to show the error screen.
 */

internal class WhatTheStackExceptionHandler(
    private val service: Messenger
) : Thread.UncaughtExceptionHandler {
    override fun uncaughtException(t: Thread, e: Throwable) {

        val exceptionData = e.process()

        service.send(Message().apply {
            data = bundleOf(
                KEY_EXCEPTION_TYPE to exceptionData.type,
                KEY_EXCEPTION_CAUSE to exceptionData.cause,
                KEY_EXCEPTION_MESSAGE to exceptionData.message,
                KEY_EXCEPTION_STACKTRACE to exceptionData.stacktrace
            )
        })

        Process.killProcess(Process.myPid())
    }
}
