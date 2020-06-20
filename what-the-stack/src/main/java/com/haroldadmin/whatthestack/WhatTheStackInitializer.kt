package com.haroldadmin.whatthestack

import android.content.Context
import androidx.startup.Initializer
import java.lang.Class

/**
 * WhatTheStackInitializer is an [androidx.startup.Initializer] for WhatTheStack
 *
 * This particular initializer does not need to return anything, but it is required to return
 * a sensible value here so we return an instance of a dummy class [WhatTheStackInitializedToken]
 * instead.
 */
class WhatTheStackInitializer : Initializer<WhatTheStackInitializedToken> {

    override fun create(context: Context): WhatTheStackInitializedToken {
        InitializationManager.init(context)
        return WhatTheStackInitializedToken()
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}

/**
 * A dummy class that does nothing but represent a type that can be returned by
 * [WhatTheStackInitializer]
 */
class WhatTheStackInitializedToken
