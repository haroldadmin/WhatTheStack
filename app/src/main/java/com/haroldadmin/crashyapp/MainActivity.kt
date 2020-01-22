package com.haroldadmin.crashyapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        crashButton.setOnClickListener {
            throw BecauseICanException()
        }
    }
}

private class BecauseICanException : Exception("This exception is purely thrown because it can be thrown")
