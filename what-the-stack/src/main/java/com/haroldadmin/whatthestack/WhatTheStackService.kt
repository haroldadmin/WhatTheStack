package com.haroldadmin.whatthestack

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger

/**
 * A Bound Service which runs in a separate process than the application.
 *
 * Messages are sent to this service when an uncaught exception is thrown in the consuming
 * application. This exception is caught using a default exception handler set by this library,
 * which processes the exception and sends it as a message to this service.
 *
 * This service then starts an activity with the processed exception data as an intent extra.
 */

class WhatTheStackService : Service() {

    private val handler by lazy { WhatTheStackHandler(applicationContext) }

    override fun onBind(intent: Intent?): IBinder? {
        return Messenger(handler).binder
    }

    internal class WhatTheStackHandler(
        private val applicationContext: Context
    ) : Handler() {
        override fun handleMessage(msg: Message) {
            val type = msg.data.getString(KEY_EXCEPTION_TYPE)
            val cause = msg.data.getString(KEY_EXCEPTION_CAUSE)
            val message = msg.data.getString(KEY_EXCEPTION_MESSAGE)
            val stacktrace = msg.data.getString(KEY_EXCEPTION_STACKTRACE)
            Intent()
                .apply {
                    setClass(applicationContext, WhatTheStackActivity::class.java)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    putExtra(KEY_EXCEPTION_TYPE, type)
                    putExtra(KEY_EXCEPTION_CAUSE, cause)
                    putExtra(KEY_EXCEPTION_MESSAGE, message)
                    putExtra(KEY_EXCEPTION_STACKTRACE, stacktrace)
                }
                .also { intent ->
                    applicationContext.startActivity(intent)
                }
        }
    }
}
