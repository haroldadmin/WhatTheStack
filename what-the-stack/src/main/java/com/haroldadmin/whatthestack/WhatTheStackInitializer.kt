package com.haroldadmin.whatthestack

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.os.Messenger
import android.util.Log
import androidx.startup.Initializer
import java.lang.Class

/**
 * WhatTheStackInitializer is an [androidx.startup.Initializer] for WhatTheStack
 */
@HostAppProcess
class WhatTheStackInitializer : Initializer<WhatTheStackInitializer.InitializedToken> {

    /**
     * Runs in the host app's process to:
     *
     * 1. Start [WhatTheStackService] as a bound service to allow communication between the
     * app's process and the service's process
     * 2. Replace the app's default [Thread.UncaughtExceptionHandler] with [WhatTheStackExceptionHandler]
     * when the service is connected.
     *
     * This method does not need to return anything, but it is required to return
     * a sensible value here so we return a dummy object [InitializedToken] instead.
     */
    override fun create(context: Context): InitializedToken {
        val connection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder) {
                val messenger = Messenger(service)
                val defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
                val customExceptionHandler = WhatTheStackExceptionHandler(
                    messenger,
                    defaultExceptionHandler
                )
                Thread.setDefaultUncaughtExceptionHandler(customExceptionHandler)
            }

            override fun onServiceDisconnected(name: ComponentName?) = Unit
        }

        val intent = Intent(context, WhatTheStackService::class.java)
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE)

        return InitializedToken
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()

    /**
     * A dummy object that does nothing but represent a type that can be returned by
     * [WhatTheStackInitializer]
     */
    object InitializedToken
}
