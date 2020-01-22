package com.haroldadmin.crashyapp

import android.app.Application
import com.haroldadmin.whatthestack.WhatTheStack

class CrashyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        WhatTheStack(this).init()
    }
}
