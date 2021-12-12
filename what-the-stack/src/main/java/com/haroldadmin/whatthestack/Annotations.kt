package com.haroldadmin.whatthestack

/**
 * Indicates that the annotated class/function runs in the host application's
 * process, not in the bound service's process
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
internal annotation class HostAppProcess

/**
 * Indicates that the annotated class/function runs in the bound service's
 * process, not in the host app's process
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
internal annotation class ServiceProcess
