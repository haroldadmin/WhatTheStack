package com.haroldadmin.whatthestack

import org.junit.Assert.*
import org.junit.Test

private const val testMessage = "This is a test message"

internal class StringOutputStreamTest {

    @Test
    fun `data write test`() {
        val outputStream = StringOutputStream()
        outputStream.write(testMessage.toByteArray())

        val actualMessage = outputStream.getString()

        assertEquals(testMessage, actualMessage)
    }

    @Test
    fun `data clear test`() {
        val outputStream = StringOutputStream().apply {
            write(testMessage.toByteArray())
        }

        outputStream.clear()

        assertTrue(outputStream.getString().isEmpty())
    }
}
