package com.efficient.logs

import java.io.File

typealias LogMessage = () -> String?

const val LOG_ON: Boolean = BuildConfig.LOG_ON

@Suppress("NOTHING_TO_INLINE")
inline fun logv() {
    if (LOG_ON) {
        InternalLogs.v(logTag(), formatMessage(""))
    }
}

inline fun logv(message: LogMessage) {
    if (LOG_ON) {
        InternalLogs.v(logTag(), formatMessage(message()))
    }
}

@Suppress("NOTHING_TO_INLINE")
inline fun logd() {
    if (LOG_ON) {
        InternalLogs.d(logTag(), formatMessage(""))
    }
}

inline fun logd(message: LogMessage) {
    if (LOG_ON) {
        InternalLogs.d(logTag(), formatMessage(message()))
    }
}

@Suppress("NOTHING_TO_INLINE")
inline fun logi() {
    if (LOG_ON) {
        InternalLogs.i(logTag(), formatMessage(""))
    }
}

inline fun logi(message: LogMessage) {
    if (LOG_ON) {
        InternalLogs.i(logTag(), formatMessage(message()))
    }
}

@Suppress("NOTHING_TO_INLINE")
inline fun logw() {
    if (LOG_ON) {
        InternalLogs.w(logTag(), formatMessage(""))
    }
}

inline fun logw(message: LogMessage) {
    if (LOG_ON) {
        InternalLogs.w(logTag(), formatMessage(message()))
    }
}

inline fun loge(message: LogMessage) {
    if (LOG_ON) {
        InternalLogs.e(logTag(), formatMessage(message()))
    }
}

@Suppress("NOTHING_TO_INLINE")
inline fun loge(throwable: Throwable) {
    if (LOG_ON) {
        InternalLogs.e(logTag(), formatMessage(getDetails(throwable)))
    }
}

inline fun loge(throwable: Throwable, message: LogMessage) {
    if (LOG_ON) {
        InternalLogs.e(logTag(), formatMessage(message()), throwable)
    }
}

fun logfile(file: File?) {
    if (LOG_ON) {
        if (file?.exists() == false) file.createNewFile(); InternalLogs._logfile = file
    }
}