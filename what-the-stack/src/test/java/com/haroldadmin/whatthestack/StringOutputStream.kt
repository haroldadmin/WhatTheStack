package com.haroldadmin.whatthestack

import java.io.OutputStream
import java.lang.StringBuilder

internal class StringOutputStream : OutputStream() {

    private val builder = StringBuilder()

    override fun write(byte: Int) {
        builder.append(byte.toChar())
    }

    fun getString() = builder.toString()

    fun clear() = builder.clear()
}
