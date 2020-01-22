package com.haroldadmin.whatthestack

internal class RootCauseException : Throwable() {

    override val message = MESSAGE

    companion object {
        internal const val MESSAGE = "root cause exception"
    }
}

internal class ExceptionWithRootCause : Throwable() {
    override val cause = RootCauseException()
    override val message = MESSAGE

    companion object {
        internal const val MESSAGE = "exception with root cause"
    }
}
