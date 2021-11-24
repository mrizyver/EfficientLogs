package com.efficient.logs

import android.util.Log
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

object InternalLogs {
    var _logfile: File? = null
    private val format by lazy { SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault()) }

    fun v(tag: String, text: String) {
        Log.v(tag, text)
        writeToFile("V", "$tag: $text")
    }

    fun d(tag: String, text: String) {
        Log.d(tag, text)
        writeToFile("D", "$tag: $text")
    }

    fun i(tag: String, text: String) {
        Log.i(tag, text)
        writeToFile("I", "$tag: $text")
    }

    fun w(tag: String, text: String) {
        Log.w(tag, text)
        writeToFile("W", "$tag: $text")
    }

    fun e(tag: String, text: String) {
        Log.e(tag, text)
        writeToFile("E", "$tag: $text")
    }

    fun e(tag: String, text: String, throwable: Throwable) {
        Log.e(tag, text, throwable)
        writeToFile("E", "$tag: $text")
    }

    private fun writeToFile(tag: String, text: String) {
        val time = format.format(Date(System.currentTimeMillis()))
        _logfile?.appendText("$time $tag/$text\n")
    }
}