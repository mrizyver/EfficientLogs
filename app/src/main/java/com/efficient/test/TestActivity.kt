package com.efficient.test

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.efficient.logs.*
import java.io.File

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val file = File(cacheDir, "log.txt")
        file.deleteOnExit()
        logfile(file)
        logv { "Hello0" }
        logd { "Hello1" }
        logprefix("/")
        logi { "Hello2" }
        logw { "Hello3" }
        println()
    }
}