# WhatTheStack

WhatTheStack is a library to make your debugging experience on Android better.

WhatTheStack shows you a pretty error screen when your Android App crashes, instead of a boring old dialog saying "Unfortunately, \<your-app\> has crashed".

## Setup

All that is needed to initialize this library is to use the `init()` method in the `onCreate()` callback of your custom Application class.

```kotlin
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
          WhatTheStack(this).init()
        }
    }
}
```

And when an error is thrown in your application, you shall be greeted with a screen similar to this:

<img src="media/screenshot.jpeg" width="360px" height="640px"/>

## Under the hood

This library works by setting a default `UncaughtExceptionHandler` on your app, and running a service to receive notifications about these exceptions.

When an uncaught exception is thrown, it is caught by this handler and sent to the service running in a _different process than your application_ to parse and display the information about the exception to you.

Running in a separate process is important because when an uncaught exception is thrown, the main thread of your application becomes unable to perform any UI related actions, and hence can't launch an intent to display the error screen shipped with this library.

## Installation

Add Jitpack repository in your root `build.gradle` file:

```groovy
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
    }
}
```

And then add the dependency to your app:

```groovy
dependencies {
  implementation 'com.github.haroldadmin:WhatTheStack:(latest-version)'
}
```

[![Release](https://jitpack.io/v/haroldadmin/WhatTheStack.svg)](https://jitpack.io/#haroldadmin/WhatTheStack)

## Contributions

Contributions to this library are very welcome.

I threw this together over one weekend, and it hasn't been thoroughly tested. Community validation and contributions would therefore be great.
