package com.efficient.logs

private var throwingAction: ((Throwable) -> Unit)? = null
fun setThrowingAction(action: (Throwable) -> Unit) {
    throwingAction = action
}

fun <T> throwing(throwable: Throwable? = null): T? {
    val exception = Error(throwable ?: RuntimeException("Something went wrong"))
    if (BuildConfig.LOG_ON) {
        throw exception
    } else {
        throwingAction?.invoke(exception)
        return null
    }
}
