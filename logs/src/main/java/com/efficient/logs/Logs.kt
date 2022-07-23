@file:Suppress("NOTHING_TO_INLINE")

package com.efficient.logs

import java.io.File

typealias LogMessage = () -> CharSequence?

const val LOG_ON: Boolean = BuildConfig.LOG_ON

inline fun logv() {
    if (LOG_ON) {
        val (tag, method) = _logAndMethodName()
        InternalLogs.v(tag, _formatMessage("", method) ?: return)
    }
}

inline fun logv(tag: String) {
    if (LOG_ON) {
        val (_, method) = _logAndMethodName()
        InternalLogs.v(tag, _formatMessage("", method) ?: return)
    }
}

inline fun logv(message: LogMessage) {
    if (LOG_ON) {
        val (tag, method) = _logAndMethodName()
        InternalLogs.v(tag, _formatMessage(message(), method) ?: return)
    }
}

inline fun logv(tag: String, message: LogMessage) {
    if (LOG_ON) {
        val (_, method) = _logAndMethodName()
        InternalLogs.v(tag, _formatMessage(message(), method) ?: return)
    }
}

inline fun logd() {
    if (LOG_ON) {
        val (tag, method) = _logAndMethodName()
        InternalLogs.d(tag, _formatMessage("", method) ?: return)
    }
}

inline fun logd(tag: String) {
    if (LOG_ON) {
        val (_, method) = _logAndMethodName()
        InternalLogs.d(tag, _formatMessage("", method) ?: return)
    }
}

inline fun logd(message: LogMessage) {
    if (LOG_ON) {
        val (tag, method) = _logAndMethodName()
        InternalLogs.d(tag, _formatMessage(message(), method) ?: return)
    }
}

inline fun logd(tag: String, message: LogMessage) {
    if (LOG_ON) {
        val (_, method) = _logAndMethodName()
        InternalLogs.d(tag, _formatMessage(message(), method) ?: return)
    }
}

inline fun logi() {
    if (LOG_ON) {
        val (tag, method) = _logAndMethodName()
        InternalLogs.i(tag, _formatMessage("", method) ?: return)
    }
}

inline fun logi(tag: String) {
    if (LOG_ON) {
        val (_, method) = _logAndMethodName()
        InternalLogs.i(tag, _formatMessage("", method) ?: return)
    }
}

inline fun logi(message: LogMessage) {
    if (LOG_ON) {
        val (tag, method) = _logAndMethodName()
        InternalLogs.i(tag, _formatMessage(message(), method) ?: return)
    }
}

inline fun logi(tag: String, message: LogMessage) {
    if (LOG_ON) {
        val (_, method) = _logAndMethodName()
        InternalLogs.i(tag, _formatMessage(message(), method) ?: return)
    }
}

inline fun logw() {
    if (LOG_ON) {
        val (tag, method) = _logAndMethodName()
        InternalLogs.w(tag, _formatMessage("", method) ?: return)
    }
}

inline fun logw(tag: String) {
    if (LOG_ON) {
        val (_, method) = _logAndMethodName()
        InternalLogs.w(tag, _formatMessage("", method) ?: return)
    }
}

inline fun logw(message: LogMessage) {
    if (LOG_ON) {
        val (tag, method) = _logAndMethodName()
        InternalLogs.w(tag, _formatMessage(message(), method) ?: return)
    }
}

inline fun logw(tag: String, message: LogMessage) {
    if (LOG_ON) {
        val (_, method) = _logAndMethodName()
        InternalLogs.w(tag, _formatMessage(message(), method) ?: return)
    }
}

inline fun loge(message: LogMessage) {
    if (LOG_ON) {
        val (tag, method) = _logAndMethodName()
        InternalLogs.e(tag, _formatMessage(message(), method) ?: return)
    }
}

inline fun loge(tag: String, message: LogMessage) {
    if (LOG_ON) {
        val (_, method) = _logAndMethodName()
        InternalLogs.e(tag, _formatMessage(message(), method) ?: return)
    }
}

inline fun loge(throwable: Throwable) {
    if (LOG_ON) {
        val (tag, method) = _logAndMethodName()
        InternalLogs.e(tag, _formatMessage(_getDetails(throwable), method) ?: return)
    }
}

inline fun loge(tag: String, throwable: Throwable) {
    if (LOG_ON) {
        val (_, method) = _logAndMethodName()
        InternalLogs.e(tag, _formatMessage(_getDetails(throwable), method) ?: return)
    }
}

inline fun loge(throwable: Throwable, message: LogMessage) {
    if (LOG_ON) {
        val (tag, method) = _logAndMethodName()
        InternalLogs.e(tag, _formatMessage(message(), method) ?: return, throwable)
    }
}

inline fun loge(tag: String, throwable: Throwable, message: LogMessage) {
    if (LOG_ON) {
        val (_, method) = _logAndMethodName()
        InternalLogs.e(tag, _formatMessage(message(), method) ?: return, throwable)
    }
}

fun logfile(file: File?) {
    if (LOG_ON) {
        if (file?.exists() == false) file.createNewFile()
        InternalLogs._logfile = file
    }
}

fun logprefix(prefix: String?) {
    if (LOG_ON) {
        LogsConfig._prefix = prefix
    }
}