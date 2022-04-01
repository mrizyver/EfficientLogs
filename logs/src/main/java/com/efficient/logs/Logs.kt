package com.efficient.logs

import java.io.File

typealias LogMessage = Configurator.() -> CharSequence?

const val LOG_ON: Boolean = BuildConfig.LOG_ON

@Suppress("NOTHING_TO_INLINE")
inline fun logv() {
    if (LOG_ON) {
        InternalLogs.v(_logTag(), _formatMessage(""))
    }
}

inline fun logv(message: LogMessage) {
    if (LOG_ON) {
        val msg = _parseMessage(Configurator.message()) ?: return
        InternalLogs.v(_logTag(), _formatMessage(msg))
    }
}

@Suppress("NOTHING_TO_INLINE")
inline fun logd() {
    if (LOG_ON) {
        InternalLogs.d(_logTag(), _formatMessage(""))
    }
}

inline fun logd(message: LogMessage) {
    if (LOG_ON) {
        val msg = _parseMessage(Configurator.message()) ?: return
        InternalLogs.d(_logTag(), _formatMessage(msg))
    }
}

@Suppress("NOTHING_TO_INLINE")
inline fun logi() {
    if (LOG_ON) {
        InternalLogs.i(_logTag(), _formatMessage(""))
    }
}

inline fun logi(message: LogMessage) {
    if (LOG_ON) {
        val msg = _parseMessage(Configurator.message()) ?: return
        InternalLogs.i(_logTag(), _formatMessage(msg))
    }
}

@Suppress("NOTHING_TO_INLINE")
inline fun logw() {
    if (LOG_ON) {
        InternalLogs.w(_logTag(), _formatMessage(""))
    }
}

inline fun logw(message: LogMessage) {
    if (LOG_ON) {
        val msg = _parseMessage(Configurator.message()) ?: return
        InternalLogs.w(_logTag(), _formatMessage(msg))
    }
}

inline fun loge(message: LogMessage) {
    if (LOG_ON) {
        val msg = _parseMessage(Configurator.message()) ?: return
        InternalLogs.e(_logTag(), _formatMessage(msg))
    }
}

@Suppress("NOTHING_TO_INLINE")
inline fun loge(throwable: Throwable) {
    if (LOG_ON) {
        InternalLogs.e(_logTag(), _formatMessage(_getDetails(throwable)))
    }
}

inline fun loge(throwable: Throwable, message: LogMessage) {
    if (LOG_ON) {
        val msg = _parseMessage(Configurator.message()) ?: return
        InternalLogs.e(_logTag(), _formatMessage(msg), throwable)
    }
}

fun logfile(file: File?) {
    if (LOG_ON) {
        if (file?.exists() == false) file.createNewFile(); InternalLogs._logfile = file
    }
}

fun logprefix(prefix: String?){
    if (LOG_ON) {
        LogsConfig._prefix = prefix
    }
}