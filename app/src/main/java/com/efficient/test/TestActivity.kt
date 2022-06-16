package com.efficient.test

import android.animation.ValueAnimator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
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
        logw { null.logNonEmpty() }
        logw { "".logNonEmpty() }
        logw { null.logNonNull() }
        logw { "Hello6" }
        val animator = ValueAnimator()
        animator.addUpdateListener {
            logv { "Hello from ValueAnimator" }
            animator.cancel()
        }
        animator.repeatCount = 1
        animator.setIntValues(0)
        animator.start()
    }
}

fun test(block: () -> Unit) = block()