package com.efficient.logs

import java.io.PrintWriter
import java.io.StringWriter

@Suppress("FunctionName" )
fun _parseMessage(charSequence: CharSequence?): String? {
    when (charSequence) {
        is Configurator.NonNull -> if (charSequence.text == null) return null
        is Configurator.NonEmpty -> if (charSequence.isNullOrEmpty()) return null
    }
    if (charSequence is Configurator.LogCharSequence) return charSequence.text.toString()
    return charSequence?.toString()
}

@Suppress("FunctionName" )
fun _formatMessage(message: String?): String {
    return if (message?.isEmpty() == true) _logMethodName()
    else "${_logMethodName()}: $message"
}

@Suppress("FunctionName" )
fun _logTag(): String {
    val index = _indexOfCurrentClassStackTrace()
    return (LogsConfig._prefix ?: "") + Thread.currentThread().stackTrace[index].className.split('.').last()
}

@Suppress("FunctionName" )
fun _logMethodName(): String {
    val index = _indexOfCurrentClassStackTrace()
    return Thread.currentThread().stackTrace[index].methodName + "()"
}

@Suppress("FunctionName", "NOTHING_TO_INLINE")
inline fun _indexOfCurrentClassStackTrace(): Int {
    return Thread.currentThread().stackTrace.indexOfLast { it.className.endsWith("LogsUtilKt") } + 1
}

@Suppress("FunctionName" )
fun _getDetails(throwable: Throwable): String {
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
