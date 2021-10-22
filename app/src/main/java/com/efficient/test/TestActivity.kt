package com.efficient.test

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.efficient.logs.logd

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logd { "Hello" }
    }
}