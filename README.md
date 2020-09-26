# WhatTheStack

![Banner](media/repo-banner.png)

WhatTheStack is a library to make your debugging experience on Android better.

It shows you a pretty error screen when your Android App crashes, instead of a boring old dialog saying "Unfortunately, \<your-app\> has crashed".

## Setup

Follow the [Installation Instructions](#installation) to set it up.

_Remember to use WhatTheStack only in debug builds of your app by using `debugImplementation` instead of `implementation.`_

Now when an uncaught exception is thrown in your application, you will be greeted with a screen similar to this:

<img src="media/screenshot.jpeg" width="360px" height="640px"/>

### Disabling automatic initialization

`WhatTheStack` initializes automatically when your application starts. It accomplishes this using Jetpack's App Startup library.

If you want to disable automatic startup, add the following lines to your Manifest file:

```xml
<provider
  android:name="androidx.startup.InitializationProvider"
  android:authorities="${applicationId}.androidx-startup"
  android:exported="false"
  tools:node="merge">
  <meta-data  android:name="com.haroldadmin.whatthestack.WhatTheStackInitializer"
    android:value="androidx.startup"
     tools:node="remove"/>
</provider>
```

## Under the hood

This library works by setting a default `UncaughtExceptionHandler` on your app, and running a service to receive notifications about thrown exceptions.

When an uncaught exception is thrown, it is caught by this handler and sent to the service running in a _different process than your application_ to parse and display information about the exception.

Running in a separate process is important because when an uncaught exception is thrown, the main thread of your application becomes unable to perform any UI related actions, and hence can't launch an intent to display the error screen shipped with this library.

## Impact on Crashlytics

WhatTheStack works by replacing the default uncaught exception handler in your app's process. Unfortunately, crash reporting libraries such as Firebase Crashlytics also work in this manner.
Since there can only be one default uncaught exception handler, WhatTheStack might break crash reporting libraries from working properly in debug builds.

This _should not be a problem for most users_ as crash reporting tools are rarely used in debug builds.

## Installation

Add Jitpack repository in your root `build.gradle` file:

```groovy
allprojects {
  repositories {
    maven { url 'https://jitpack.io' }
  }
}
```

And then add the dependency to your app:

```groovy
dependencies {
  debugImplementation 'com.github.haroldadmin:WhatTheStack:(latest-version)'
}
```

It is not recommended to use WhatTheStack in anything other than debug builds of your app. Only use `debugImplementation` when adding this dependency.

[![Release](https://jitpack.io/v/haroldadmin/WhatTheStack.svg)](https://jitpack.io/#haroldadmin/WhatTheStack)

## Contributions

Contributions to this library are very welcome.
