package com.efficient.test

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.efficient.logs.logd
import com.efficient.logs.logfile
import java.io.File

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val file = File(cacheDir, "log.txt")
        file.deleteOnExit()
        logfile(file)
        logd { "Hello0" }
        logd { "Hello1" }
        logd { "Hello2" }
        logd { "Hello3" }
        println()
    }
}