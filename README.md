# WhatTheStack

WhatTheStack is a library to make your debugging experience on Android better.

It shows you a pretty error screen when your Android App crashes, instead of a boring old dialog saying "Unfortunately, \<your-app\> has crashed".

## Setup

Follow the [Installation Instructions](#installation) to set it up.

Now when an uncaught exception is thrown in your application, you shall be greeted with a screen similar to this:

<img src="media/screenshot.jpeg" width="360px" height="640px"/>

### Disabling automatic initialization

`WhatTheStack` initializes automatically when your application starts. It accomplishes this using a `ContentProvider`.

If you want to disable automatic initialization, you should disable the initialization content provider of this library by adding the following lines to your application's `AndroidManifest.xml` file:

```xml
<provider
  android:name="com.haroldadmin.whatthestack.WhatTheStackInitProvider"
  android:authorities="${applicationId}.WhatTheStackInitProvider"
  tools:node="remove" />
```

## Under the hood

This library works by setting a default `UncaughtExceptionHandler` on your app, and running a service to receive notifications about these exceptions.

When an uncaught exception is thrown, it is caught by this handler and sent to the service running in a _different process than your application_ to parse and display the information about the exception to you.

Running in a separate process is important because when an uncaught exception is thrown, the main thread of your application becomes unable to perform any UI related actions, and hence can't launch an intent to display the error screen shipped with this library.

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

I threw this together over one weekend, and it hasn't been thoroughly tested. Community validation and contributions would therefore be great.
