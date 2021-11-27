package com.haroldadmin.whatthestack.ui.preview

object SampleData {
    const val ExceptionType = "Runtime Exception"

    const val ExceptionMessage = "This exception was thrown purely because it can be thrown"

    const val Stacktrace =
        """java.lang.RuntimeException: java.lang.reflect.InvocationTargetException
	at com.android.internal.os.RuntimeInitMethodAndArgsCaller.run(RuntimeInit.java:558)
	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:1003)
Caused by: java.lang.reflect.InvocationTargetException
	at java.lang.reflect.Method.invoke(Native Method)
	at com.android.internal.os.RuntimeInitMethodAndArgsCaller.run(RuntimeInit.java:548)
	... 1 more
Caused by: com.haroldadmin.crashyapp.BecauseICanException: This exception is thrown purely because it can be thrown
	at com.haroldadmin.crashyapp.MainActivity.onCreatelambda-0(MainActivity.kt:15)
	at com.haroldadmin.crashyapp.MainActivity.r8lambdapFZVHP1EeT4E2LW7TLA5yGBRTTk(Unknown Source:0)
	at com.haroldadmin.crashyapp.MainActivityxternalSyntheticLambda0.onClick(Unknown Source:0)
	at android.view.View.performClick(View.java:7441)
	at com.google.android.material.button.MaterialButton.performClick(MaterialButton.java:1119)
	at android.view.View.performClickInternal(View.java:7418)
	at android.view.View.access$3700(View.java:835)
	at android.view.ViewPerformClick.run(View.java:28676)
	at android.os.Handler.handleCallback(Handler.java:938)
	at android.os.Handler.dispatchMessage(Handler.java:99)
	at android.os.Looper.loopOnce(Looper.java:201)
	at android.os.Looper.loop(Looper.java:288)
	at android.app.ActivityThread.main(ActivityThread.java:7839)
	... 3 more
"""
}
