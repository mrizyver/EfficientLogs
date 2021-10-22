package com.efficient.logs

import android.util.Log
import java.io.PrintWriter
import java.io.StringWriter

typealias LogMessage = () -> String?

const val LOG_ON: Boolean = BuildConfig.LOG_ON

@Suppress("NOTHING_TO_INLINE")
inline fun logv() {
    if (LOG_ON) {
        Log.v(logTag(), formatMessage(""))
    }
}

inline fun logv(message: LogMessage) {
    if (LOG_ON) {
        Log.v(logTag(), formatMessage(message()))
    }
}

@Suppress("NOTHING_TO_INLINE")
inline fun logd() {
    if (LOG_ON) {
        Log.d(logTag(), formatMessage(""))
    }
}

inline fun logd(message: LogMessage) {
    if (LOG_ON) {
        Log.d(logTag(), formatMessage(message()))
    }
}

@Suppress("NOTHING_TO_INLINE")
inline fun logi() {
    if (LOG_ON) {
        Log.i(logTag(), formatMessage(""))
    }
}

inline fun logi(message: LogMessage) {
    if (LOG_ON) {
        Log.i(logTag(), formatMessage(message()))
    }
}

@Suppress("NOTHING_TO_INLINE")
inline fun logw() {
    if (LOG_ON) {
        Log.w(logTag(), formatMessage(""))
    }
}

inline fun logw(message: LogMessage) {
    if (LOG_ON) {
        Log.w(logTag(), formatMessage(message()))
    }
}

inline fun loge(message: LogMessage) {
    if (LOG_ON) {
        Log.e(logTag(), formatMessage(message()))
    }
}

@Suppress("NOTHING_TO_INLINE")
inline fun loge(throwable: Throwable) {
    if (LOG_ON) {
        Log.e(logTag(), formatMessage(getDetails(throwable)))
    }
}

inline fun loge(throwable: Throwable, message: LogMessage) {
    if (LOG_ON) {
        Log.e(logTag(), formatMessage(message()), throwable)
    }
}

fun formatMessage(message: String?): String {
    return if (message?.isEmpty() == true) logMethodName()
    else "${logMethodName()}: $message"
}

fun logTag(): String {
    val index = indexOfCurrentClassStackTrace()
    return Thread.currentThread().stackTrace[index].className.split('.').last()
}

fun logMethodName(): String {
    val index = indexOfCurrentClassStackTrace()
    return Thread.currentThread().stackTrace[index].methodName + "()"
}

@Suppress("NOTHING_TO_INLINE")
inline fun indexOfCurrentClassStackTrace(): Int {
    return Thread.currentThread().stackTrace.indexOfLast { it.className.endsWith("LogsKt") } + 1
}

fun getDetails(throwable: Throwable): String {
    val stringWriter = StringWriter()
    val printWriter = PrintWriter(stringWriter)
    try {
        throwable.printStackTrace(printWriter)
        return stringWriter.toString()
    } finally {
        stringWriter.close()
        printWriter.close()
    }
}