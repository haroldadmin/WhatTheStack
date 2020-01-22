package com.haroldadmin.whatthestack

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder

internal class WhatTheStackConnection(
    private val onDisconnected: () -> Unit = { Unit },
    private val onConnected: (IBinder) -> Unit
) : ServiceConnection {

    override fun onServiceDisconnected(name: ComponentName?) {
        onDisconnected()
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder) {
        onConnected(service)
    }
}
