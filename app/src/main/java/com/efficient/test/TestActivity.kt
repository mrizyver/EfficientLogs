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

        val obj = object {
            val runnable = Runnable {
                test {
                    logv { "Runnable1" }
                }
                logv { "Runnable2" }
            }
        }
        obj.runnable.run()
        logv { "" }
        logv { "Hello0" }
        logd { "Hello1" }
        logprefix("/")
        logi { "Hello2" }
        logw { "Hello3" }
        logw { nonempty("") }
        logw { nonnull(null) }
        logw { "Hello6" }
        println()
    }

    fun test(block: () -> Unit) = block()
}