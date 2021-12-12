package com.haroldadmin.whatthestack

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.*
import android.util.Log

/**
 * A Bound Service which runs in a separate process than the host application.
 *
 * This service must be started with [Context.bindService]. A bound service lives only as long as
 * the calling context, so `bindService` must be called on an **APPLICATION CONTEXT**.
 *
 * [WhatTheStackInitializer] starts this service, and it dies when the host app terminates.
 * Therefore we don't need to explicitly handle [Service.onCreate], [Service.onDestroy] or call
 * [Service.stopSelf].
 *
 * [WhatTheStackExceptionHandler] sends messages to this service whenever an uncaught exception
 * is thrown in the host application. This service then starts an activity with the processed
 * exception data as an intent extra.
 */
@ServiceProcess
class WhatTheStackService : Service() {
    /**
     * [Handler] that runs on the main thread to handle incoming processed uncaught
     * exceptions from [WhatTheStackExceptionHandler]
     *
     * We need to lazily initialize it because [getApplicationContext] returns null right
     * after the service is created.
     */
    private val handler by lazy { WhatTheStackHandler(applicationContext) }

    /**
     * Runs when [WhatTheStackInitializer] calls [Context.bindService] to create a connection
     * to this service.
     *
     * It creates a [Messenger] that can be used to communicate with its [handler],
     * and returns its [IBinder].
     */
    override fun onBind(intent: Intent?): IBinder? {
        val messenger = Messenger(handler)
        return messenger.binder
    }
}

/**
 * A [Handler] that runs on the main thread of the service process to process
 * incoming uncaught exception messages.
 */
@ServiceProcess
private class WhatTheStackHandler(
    private val applicationContext: Context
) : Handler(Looper.getMainLooper()) {

    override fun handleMessage(msg: Message) {
        Intent()
            .apply {
                setClass(applicationContext, WhatTheStackActivity::class.java)
                putExtras(msg.data)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            .also { intent ->
                applicationContext.startActivity(intent)
            }
    }
}