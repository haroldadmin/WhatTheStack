package com.haroldadmin.whatthestack

import android.content.Context
import android.content.Intent
import android.os.Messenger

/**
 * A class to allow initialization of WhatTheStack service.
 *
 * @param applicationContext The context used to start the service to catch uncaught exceptions
 */
class WhatTheStack(private val applicationContext: Context) {

    private val connection = WhatTheStackConnection(
        onConnected = { binder ->
            val messenger = Messenger(binder)
            val exceptionHandler = WhatTheStackExceptionHandler(messenger)
            Thread.setDefaultUncaughtExceptionHandler(exceptionHandler)
        }
    )

    fun init() {
        val intent = Intent(applicationContext, WhatTheStackService::class.java)
        applicationContext.bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }
}
