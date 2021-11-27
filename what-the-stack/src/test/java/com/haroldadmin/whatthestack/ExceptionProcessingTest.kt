package com.haroldadmin.whatthestack

import org.junit.Test
import java.io.PrintStream

internal class ExceptionProcessingTest {

    @Test
    fun `root cause test`() {
        val exceptionWithRootCause = ExceptionWithRootCause()
        val rootCause = exceptionWithRootCause.rootCause()

        assert(rootCause is RootCauseException)
    }

    @Test
    fun `exception message test`() {
        val exception = ExceptionWithRootCause()
        val message = exception.message

        assert(message == ExceptionWithRootCause.MESSAGE)
    }

    @Test
    fun `exception type test`() {
        val exception = ExceptionWithRootCause()
        val type = exception.type()

        assert(type == ExceptionWithRootCause::class.java.simpleName)
    }

    @Test
    fun `exception stacktrace test`() {
        val exception = ExceptionWithRootCause()
        val outputStream = StringOutputStream()
        exception.printStackTrace(PrintStream(outputStream))

        assert(outputStream.getString() == exception.stackTraceToString())
    }

    @Test
    fun `exception processing test`() {
        val exception = ExceptionWithRootCause()
        val processedData = exception.process()

        assert(processedData.type == exception.type())
        assert(processedData.message == exception.rootCause().message)
        assert(processedData.stacktrace == exception.stackTraceToString())
        assert(processedData.cause == exception.rootCause().type())
    }
}
