package com.efficient.logs

import java.io.PrintWriter
import java.io.StringWriter

@Suppress("FunctionName")
fun _formatMessage(message: CharSequence?): String? {
    when (message) {
        is NonNull -> if (message.text == null) return null
        is NonEmpty -> if (message.text?.isEmpty() == true) return null
    }
    val msg = when(message) {
        is String -> message
        is LogCharSequence -> message.text
        else -> message.toString()
    }
    return if (msg?.isEmpty() == true) _logMethodName()
    else "${_logMethodName()}: $msg"
}

@Suppress("FunctionName")
fun _logTag(): String {
    val index = _indexOfCurrentClassStackTrace()
    val className = Thread.currentThread().stackTrace[index].className.split('.').last()
    val nameParts = className.split("$").filter { it.length > 1 || it.getOrNull(0)?.isLetter() ?: false }
    return (LogsConfig._prefix ?: "") + (nameParts.getOrNull(0) ?: className)
}

@Suppress("FunctionName")
fun _logMethodName(): String {
    val index = _indexOfCurrentClassStackTrace()
    val methodName = Thread.currentThread().stackTrace[index].methodName
    val methodNameParts = methodName.split()
    val className = Thread.currentThread().stackTrace[index].className.split('.').last()
    val classNameParts = className.split()
    if (classNameParts.size > 1) return "${classNameParts[1]}()"
    if (methodNameParts.size > 1) return "${methodNameParts[0]}()"
    return "$methodName()"
}

@Suppress("FunctionName", "NOTHING_TO_INLINE")
inline fun _indexOfCurrentClassStackTrace(): Int {
    return Thread.currentThread().stackTrace.indexOfLast { it.className.endsWith("LogsUtilKt") } + 1
}

@Suppress("FunctionName")
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
private fun String.split() = split("$").filter { it.length > 1 || it.getOrNull(0)?.isLetter() ?: false }
