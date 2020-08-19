package com.haroldadmin.whatthestack

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Represents the data of the exception to be displayed to the user.
 *
 * @param type The class of the exception
 * @param message The message of the exception
 * @param stacktrace The stacktrace of the exception represented as a string
 */
@Parcelize
data class ExceptionData(
    val type: String,
    val cause: String,
    val message: String,
    val stacktrace: String
) : Parcelable

/**
 * Processes the given exception to produce [ExceptionData]
 *
 * The returned [ExceptionData] contains the processed values for the root cause of the exception.
 */
internal fun Throwable.process(): ExceptionData {
    val type = type()
    val rootCauseOfException = rootCause()
    val cause = rootCauseOfException.type()
    val message = rootCauseOfException.message ?: "Unknown"
    val stacktrace = this.stackTraceToString()
    return ExceptionData(type, cause, message, stacktrace)
}

/**
 * Finds and returns the root cause of the exception.
 *
 * The cause tree is traversed recursively to find the last cause.
 * If the original exception has no cause, then it itself is returned.
 */
internal tailrec fun Throwable.rootCause(): Throwable {
    return if (cause == null) {
        this
    } else {
        cause!!.rootCause()
    }
}

/**
 * Returns the class name of the exception
 */
internal fun Throwable.type(): String {
    return this::class.java.simpleName
}
