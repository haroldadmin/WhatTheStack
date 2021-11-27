package com.haroldadmin.crashyapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.haroldadmin.crashyapp.ui.pages.HomePage
import com.haroldadmin.crashyapp.ui.theme.CrashyAppTheme

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CrashyAppTheme {
                HomePage()
            }
        }
    }
}
