package com.efficient.logs

import java.io.PrintWriter
import java.io.StringWriter

fun formatMessage(message: String?): String {
    return if (message?.isEmpty() == true) logMethodName()
    else "${logMethodName()}: $message"
}

fun logTag(): String {
    val index = indexOfCurrentClassStackTrace()
    return LogsConfig._prefix ?: "" + Thread.currentThread().stackTrace[index].className.split('.').last()
}

fun logMethodName(): String {
    val index = indexOfCurrentClassStackTrace()
    return Thread.currentThread().stackTrace[index].methodName + "()"
}

@Suppress("NOTHING_TO_INLINE")
inline fun indexOfCurrentClassStackTrace(): Int {
    return Thread.currentThread().stackTrace.indexOfLast { it.className.endsWith("LogsUtilKt") } + 1
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
