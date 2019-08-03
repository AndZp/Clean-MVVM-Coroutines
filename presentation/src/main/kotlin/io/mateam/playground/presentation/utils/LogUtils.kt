package io.mateam.playground.presentation.utils

import timber.log.Timber

/****** Logging extestions ******/
fun Any.logDebug(tag:String, log: String) {
    Timber.tag(tag).d("Thread[${Thread.currentThread().name}] $log")
}

fun Any.logDebug(log: String) {
    Timber.tag(getClassName()).d("Thread[${Thread.currentThread().name}] $log")
}

fun Any.logWarning(log: String, tr: Throwable? = null) {
    Timber.tag(getClassName()).w(tr, "Thread[" + Thread.currentThread().name + "] " + log)
}

fun Any.logError(log: String, tr: Throwable? = null) {
    Timber.tag(getClassName()).e(tr,  "Thread[" + Thread.currentThread().name + "] " + log)
}

private fun Any.getClassName(): String {
    return if (this::class.java.simpleName.isNotEmpty()) this::class.java.simpleName else this::class.java.name
}