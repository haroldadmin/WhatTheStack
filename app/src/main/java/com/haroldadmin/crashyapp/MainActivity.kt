package com.haroldadmin.crashyapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.haroldadmin.crashyapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.crashButton.setOnClickListener {
            throw BecauseICanException()
        }
    }
}

private class BecauseICanException :
    Exception("This exception is thrown purely because it can be thrown")
