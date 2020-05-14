package com.haroldadmin.whatthestack

import android.content.Context
import android.content.Intent
import android.os.Messenger

/**
 * **DO NOT USE**
 * A class to allow initialization of WhatTheStack service.
 *
 * WhatTheStack initializes automatically using a content provider. You do not need to initialize
 * it explicitly using this class.
 *
 * @param applicationContext The context used to start the service to catch uncaught exceptions
 */
@Deprecated(
    "WhatTheStack initializes automatically at application startup. Do not explicitly initialize it",
    level = DeprecationLevel.ERROR
)
class WhatTheStack(private val applicationContext: Context) {

    @Suppress("unused")
    fun init() {
        WhatTheStackInitializer.init(applicationContext)
    }
}

internal object WhatTheStackInitializer {

    private var isInitialized: Boolean = false

    private val connection = WhatTheStackConnection(
        onConnected = { binder ->
            val messenger = Messenger(binder)
            val exceptionHandler = WhatTheStackExceptionHandler(messenger)
            Thread.setDefaultUncaughtExceptionHandler(exceptionHandler)
        }
    )

    fun init(applicationContext: Context) {
        if (isInitialized) { return }
        isInitialized = true
        val intent = Intent(applicationContext, WhatTheStackService::class.java)
        applicationContext.bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }
}
