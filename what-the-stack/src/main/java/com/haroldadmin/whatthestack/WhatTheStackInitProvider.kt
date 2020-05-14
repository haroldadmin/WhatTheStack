package com.haroldadmin.whatthestack

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.pm.ProviderInfo
import android.database.Cursor
import android.net.Uri
import android.util.Log

private const val TAG = "WhatTheStackInitializer"

/**
 * A content provider to initialize WhatTheStack automatically at application startup. It does not
 * provide the ability to query underlying data.
 */
class WhatTheStackInitProvider : ContentProvider() {

    override fun onCreate(): Boolean {
        val applicationContext = context ?: return false
        WhatTheStackInitializer.init(applicationContext)
        Log.d(TAG, "Initialized")
        return true
    }

    override fun attachInfo(context: Context?, info: ProviderInfo?) {
        super.attachInfo(context, info)
        checkProperAuthority(info)
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw UnsupportedOperationException("This content provider can only be used for initializing WhatTheStack")
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        throw UnsupportedOperationException("This content provider can only be used for initializing WhatTheStack")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        throw UnsupportedOperationException("This content provider can only be used for initializing WhatTheStack")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        throw UnsupportedOperationException("This content provider can only be used for initializing WhatTheStack")
    }

    override fun getType(uri: Uri): String? {
        throw UnsupportedOperationException("This content provider can only be used for initializing WhatTheStack")
    }

    /**
     * Checks to see if the application using this library has declared an applicationId in its
     * build.gradle file. If not, it crashes the app with a proper error message.
     *
     * Performing this check at startup is important because if a user's device has multiple apps
     * installed with WhatTheStack, and they do not declare their application IDs, then the content
     * providers in those applications will fallback to the same name. Since the authority is used
     * to identify the content provider, it **must** be unique.
     */
    private fun checkProperAuthority(info: ProviderInfo?) {
        val contentProviderName = WhatTheStackInitProvider::class.java.name
        require(info?.authority != contentProviderName) {
            "Please provide an applicationId inside your application's build.gradle file"
        }
    }
}
