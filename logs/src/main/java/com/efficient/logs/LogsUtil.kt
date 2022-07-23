package com.efficient.logs

import java.io.PrintWriter
import java.io.StringWriter
import java.lang.reflect.Field
import java.lang.reflect.Method

@Suppress("FunctionName")
fun _formatMessage(message: CharSequence?): String? {
    when (message) {
        is NonNull -> if (message.text == null) return null
        is NonEmpty -> if (message.text?.isEmpty() == true) return null
    }
    val msg = when (message) {
        is String -> message
        is LogCharSequence -> message.text
        else -> message.toString()
    }
    return if (msg?.isEmpty() == true) _logMethodName()
    else "${_logMethodName()}: $msg"
}

@Suppress("FunctionName")
fun _logTag(): String {
    return _logAndMethodName().tag
}

@Suppress("FunctionName")
fun _logMethodName(): String {
    return _logAndMethodName().method
}

data class TagAndName(val tag: String, val method: String)

@Suppress("FunctionName")
fun _logAndMethodName(): TagAndName {
    val index = _indexOfCurrentClassStackTrace()
    val element = Thread.currentThread().stackTrace[index]
    val className = element.className.split('.').last()
    val methodName = element.methodName
    val classNameParts = className.split("$")
    val methodNameParts = methodName.split("$")
    val (isInFunctionConstruction, isLambda, isClassField) = getMetaData(element.className, methodNameParts)
    if (isLambda && isClassField) {
        return TagAndName(classNameParts[0], "${classNameParts[1]}.$methodName()")
    } else if (isClassField) {
        return TagAndName(classNameParts[0], methodNameParts.first())
    } else if (isLambda && methodName == "invoke") {
        val additionalName = classNameParts[2].takeUnless { it.matches(Regex("[0-9]+")) } ?: ""
        return TagAndName(classNameParts[0], "${classNameParts[1]}(${additionalName})")
    } else if (isLambda) {
        return TagAndName(classNameParts[0], "${classNameParts[1]}($methodName)")
    } else if (isInFunctionConstruction) {
        return TagAndName(classNameParts[0], "${classNameParts[1]}($methodName)")
    } else if (classNameParts.size == 1 && methodNameParts.size > 1) {
        return TagAndName(classNameParts[0], "${methodNameParts[0]}(${methodNameParts[1]})")
    } else if (classNameParts.size > 1) {
        return TagAndName(classNameParts[1], "$methodName()")
    } else {
        val tag = (LogsConfig._prefix ?: "") + (classNameParts.getOrNull(0) ?: className)
        val method = "$methodName()"
        return TagAndName(tag, method)
    }
}

data class ClassNameData(
    val isInFunctionConstruction: Boolean,
    val isLambda: Boolean,
    val isClassField: Boolean
)

private fun getMetaData(fullClassName: String, methodNameParts: List<String>): ClassNameData {
    val classNameParts = fullClassName.split("$")
    val clazz = Class.forName(classNameParts.first())
    val fields = clazz.declaredFields.map(Field::getName).toSet()
    val methods = clazz.declaredMethods.map(Method::getName).toSet()
    val isLambda = classNameParts.last().matches(Regex("[0-9]+\$"))
    val isClassField = classNameParts.any(fields::contains) || methodNameParts.any(fields::contains)
    val isInFunctionConstruction = classNameParts.any(methods::contains)
    return ClassNameData(isInFunctionConstruction, isLambda, isClassField)
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