package com.efficient.logs

import java.io.PrintWriter
import java.io.StringWriter
import java.lang.reflect.Field
import java.lang.reflect.Method

@Suppress("FunctionName")
fun _formatMessage(message: CharSequence?, methodName: String): String? {
    when (message) {
        is NonNull -> if (message.text == null) return null
        is NonEmpty -> if (message.text?.isEmpty() == true) return null
    }
    val msg = when (message) {
        is String -> message
        is LogCharSequence -> message.text
        else -> message.toString()
    }
    return if (msg?.isEmpty() == true) methodName
    else "${methodName}: $msg"
}

data class TagAndName(val tag: String, val method: String, val prefix: String)

@Suppress("FunctionName")
fun _logAndMethodName(): TagAndName = try {
    val index = _indexOfCurrentClassStackTrace()
    val element = Thread.currentThread().stackTrace[index]
    val className = element.className.split('.').last()
    val methodName = element.methodName
    val prefix = LogsConfig._prefix ?: ""
    try {
        val classNameParts = className.split("$")
        val methodNameParts = methodName.split("$")
        val (isInFunctionConstruction, isLambda, isClassField) = getMetaData(element.className, methodNameParts)
        if (isLambda && isClassField && methodName == "invoke") {
            TagAndName(
                prefix + classNameParts[0],
                classNameParts[classNameParts.lastIndex - 1],
                prefix
            )
        } else if (isLambda && isClassField) {
            TagAndName(
                prefix + classNameParts[0],
                "${classNameParts[classNameParts.lastIndex - 1]}.$methodName()",
                prefix
            )
        } else if (isClassField) {
            TagAndName(prefix + classNameParts[0], methodNameParts.first(), prefix)
        } else if (isLambda && methodName == "invoke") {
            val additionalName = classNameParts[2].takeUnless { it.matches(Regex("[0-9]+")) } ?: ""
            TagAndName(
                prefix + classNameParts[0],
                "${classNameParts[1]}(${additionalName})",
                prefix
            )
        } else if (isLambda) {
            val additionalName = methodNameParts[0].takeUnless { it == "invoke" } ?: ""
            TagAndName(prefix + classNameParts[0], "${classNameParts[1]}($additionalName)", prefix)
        } else if (isInFunctionConstruction) {
            TagAndName(prefix + classNameParts[0], "${classNameParts[1]}($methodName)", prefix)
        } else if (classNameParts.size == 1 && methodNameParts.size > 1) {
            TagAndName(
                prefix + classNameParts[0],
                "${methodNameParts[0]}(${methodNameParts[1]})",
                prefix
            )
        } else if (classNameParts.size > 1) {
            TagAndName(prefix + classNameParts[1], "$methodName()", prefix)
        } else {
            val tag = classNameParts.getOrNull(0) ?: className
            val method = "$methodName()"
            TagAndName(prefix + tag, method, prefix)
        }
    } catch (e: Exception) {
        TagAndName(prefix + className, methodName, prefix)
    }
} catch (e: Exception) {
    TagAndName("", "", "")
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