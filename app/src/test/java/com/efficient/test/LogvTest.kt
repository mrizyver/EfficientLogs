package com.efficient.test

import android.util.Log
import com.efficient.logs.*
import io.mockk.mockkStatic
import io.mockk.verify
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Test
import java.lang.Runnable

fun outOfClassFunction() {
    logv()
    logd()
    logi()
    logw()
}

class CoroutineInInitBlockOutOfClass(scope: CoroutineScope) {
    init {
        scope.launch {
            flow {
                emit(1)
            }.collect{
                logv { "$it"}
                logd { "$it"}
                logi { "$it"}
                logw { "$it"}
            }
        }
    }
}

internal class LogvTest {

    class CoroutineInInitBlockInClass(scope: CoroutineScope) {
        init {
            scope.launch {
                flow {
                    emit(1)
                }.collect{
                    logv { "$it"}
                    logd { "$it"}
                    logi { "$it"}
                    logw { "$it"}
                }
            }
        }
    }

    private class LogHolder {
        fun printLog() {
            logv()
            logd()
            logi()
            logw()
        }
    }

    private class LambdaInvoker {
        fun invokeLambda(block: () -> Unit) {
            block()
        }
    }

    private class LambdaHolder {
        private var block: (() -> Unit)? = null
        fun setLambda(block: () -> Unit) {
            this.block = block
        }

        fun invokeLambda() {
            block!!.invoke()
        }
    }

    private val inClassObject = object {
        fun printInClassObjectLog() {
            logv()
            logd()
            logi()
            logw()
        }
    }

    private val inClassNestedObject = object {
       val nestedObject = object {
            fun printInClassNestedObjectLog() {
                logv()
                logd()
                logi()
                logw()
            }
        }
        fun method(){
            nestedObject.printInClassNestedObjectLog()
        }
    }

    private val runnable = Runnable {
        logv()
        logd()
        logi()
        logw()
    }

    private val lambda = {
        logv()
        logd()
        logi()
        logw()
    }

    @Before
    fun before() {
        mockkStatic(Log::class)
        logprefix("prefix_")
    }

    @Test
    fun `test with enclosing brackets`() {
        //class name 'LogsTest'
        logv()
        logd()
        logi()
        logw()
        verify { Log.v("prefix_LogvTest", "test with enclosing brackets()") }
        verify { Log.d("prefix_LogvTest", "test with enclosing brackets()") }
        verify { Log.i("prefix_LogvTest", "test with enclosing brackets()") }
        verify { Log.w("prefix_LogvTest", "test with enclosing brackets()") }
    }

    @Test
    fun `test in static class inside other class`() {
        //class name 'LogsTest$LogHolder'
        LogHolder().printLog()
        verify { Log.v("prefix_LogHolder", "printLog()") }
    }

    @Test
    fun `test in lambda invoker`() {
        //class name 'LogsTest$test in lambda invoker$1'
        LambdaInvoker().invokeLambda {
            logv()
            logd()
            logi()
            logw()
        }
        verify { Log.v("prefix_LogvTest", "test in lambda invoker()") }
        verify { Log.d("prefix_LogvTest", "test in lambda invoker()") }
        verify { Log.i("prefix_LogvTest", "test in lambda invoker()") }
        verify { Log.w("prefix_LogvTest", "test in lambda invoker()") }
    }

    @Test
    fun `test in lambda holder`() {
        //class name 'LogsTest$test in lambda holder$1'
        val holder = LambdaHolder()
        holder.setLambda {
            logv()
            logd()
            logi()
            logw()
        }
        holder.invokeLambda()
        verify { Log.v("prefix_LogvTest", "test in lambda holder()") }
        verify { Log.d("prefix_LogvTest", "test in lambda holder()") }
        verify { Log.i("prefix_LogvTest", "test in lambda holder()") }
        verify { Log.w("prefix_LogvTest", "test in lambda holder()") }
    }

    @Test
    fun `test in lambda holder with nesting`() {
        //class name 'LogsTest$test in lambda holder$1'
        val holder = LambdaHolder()
        holder.setLambda {
            val runnable = Runnable {
                logv()
                logd()
                logi()
                logw()
            }
            runnable.run()
        }
        holder.invokeLambda()
        verify { Log.v("prefix_LogvTest", "test in lambda holder with nesting()") }
        verify { Log.d("prefix_LogvTest", "test in lambda holder with nesting()") }
        verify { Log.i("prefix_LogvTest", "test in lambda holder with nesting()") }
        verify { Log.w("prefix_LogvTest", "test in lambda holder with nesting()") }
    }

    @Test
    fun `test in lambda holder with double nesting`() {
        //class name 'LogsTest$test in lambda holder$1'
        val holder = LambdaHolder()
        holder.setLambda {
            val runnable = Runnable {
                val runnable = Runnable {
                    logv()
                    logd()
                    logi()
                    logw()
                }
                runnable.run()
            }
            runnable.run()
        }
        holder.invokeLambda()
        verify { Log.v("prefix_LogvTest", "test in lambda holder with double nesting()") }
        verify { Log.d("prefix_LogvTest", "test in lambda holder with double nesting()") }
        verify { Log.i("prefix_LogvTest", "test in lambda holder with double nesting()") }
        verify { Log.w("prefix_LogvTest", "test in lambda holder with double nesting()") }
    }

    @Test
    fun `test with in function class`() {
        //class name 'LogsTest$test with in function class$InFunctionClass'
        class InFunctionClass {
            fun printInFunctionLog() {
                logv()
                logd()
                logi()
                logw()
            }
        }
        InFunctionClass().printInFunctionLog()
        verify { Log.v("prefix_LogvTest", "test with in function class(printInFunctionLog)") }
        verify { Log.d("prefix_LogvTest", "test with in function class(printInFunctionLog)") }
        verify { Log.i("prefix_LogvTest", "test with in function class(printInFunctionLog)") }
        verify { Log.w("prefix_LogvTest", "test with in function class(printInFunctionLog)") }
    }

    @Test
    fun `test with in function class and nesting`() {
        //class name 'LogsTest$test with in function class$InFunctionClass'
        class InFunctionClass {
            inner class NestedClass{
                fun nestedMethod(){
                    logv()
                    logd()
                    logi()
                    logw()
                }
            }
            fun method() {
                NestedClass().nestedMethod()
            }
        }
        InFunctionClass().method()
        verify { Log.v("prefix_LogvTest", "test with in function class and nesting(nestedMethod)") }
        verify { Log.d("prefix_LogvTest", "test with in function class and nesting(nestedMethod)") }
        verify { Log.i("prefix_LogvTest", "test with in function class and nesting(nestedMethod)") }
        verify { Log.w("prefix_LogvTest", "test with in function class and nesting(nestedMethod)") }
    }

    @Test
    fun `test with in function class and nested another lambda`() {
        //class name 'LogsTest$test with in function class$InFunctionClass'
        class InFunctionClass {
            val anotherLambda = {
                logv()
                logd()
                logi()
                logw()
            }
            fun method() {
                anotherLambda()
            }
        }
        InFunctionClass().method()
        verify { Log.v("prefix_LogvTest", "test with in function class and nested another lambda(InFunctionClass)") }
        verify { Log.d("prefix_LogvTest", "test with in function class and nested another lambda(InFunctionClass)") }
        verify { Log.i("prefix_LogvTest", "test with in function class and nested another lambda(InFunctionClass)") }
        verify { Log.w("prefix_LogvTest", "test with in function class and nested another lambda(InFunctionClass)") }
    }

    @Test
    fun `test with in function object`() {
        //class name 'LogsTest$test with in function object$inFunctionObject$1'
        val inFunctionObject = object {
            fun printInFunctionObjectLog() {
                logv()
                logd()
                logi()
                logw()
            }
        }
        inFunctionObject.printInFunctionObjectLog()
        verify { Log.v("prefix_LogvTest", "test with in function object(printInFunctionObjectLog)") }
        verify { Log.d("prefix_LogvTest", "test with in function object(printInFunctionObjectLog)") }
        verify { Log.i("prefix_LogvTest", "test with in function object(printInFunctionObjectLog)") }
        verify { Log.w("prefix_LogvTest", "test with in function object(printInFunctionObjectLog)") }
    }

    @Test
    fun `test with in function object and function invoke`() {
        //class name 'LogsTest$test with in function object$inFunctionObject$1'
        val inFunctionObject = object {
            fun invoke() {
                logv()
                logd()
                logi()
                logw()
            }
        }
        inFunctionObject.invoke()
        verify { Log.v("prefix_LogvTest", "test with in function object and function invoke(inFunctionObject)") }
        verify { Log.d("prefix_LogvTest", "test with in function object and function invoke(inFunctionObject)") }
        verify { Log.i("prefix_LogvTest", "test with in function object and function invoke(inFunctionObject)") }
        verify { Log.w("prefix_LogvTest", "test with in function object and function invoke(inFunctionObject)") }
    }

    @Test
    fun `test with in function lambda`() {
        //class name 'LogsTest$test with in function lambda$inFunctionLambda$1'
        val inFunctionLambda = {
            logv()
            logd()
            logi()
            logw()
        }
        inFunctionLambda()
        verify { Log.v("prefix_LogvTest", "test with in function lambda(inFunctionLambda)") }
        verify { Log.d("prefix_LogvTest", "test with in function lambda(inFunctionLambda)") }
        verify { Log.i("prefix_LogvTest", "test with in function lambda(inFunctionLambda)") }
        verify { Log.w("prefix_LogvTest", "test with in function lambda(inFunctionLambda)") }
    }

    @Test
    fun `test with in function function`() {
        //class name 'LogsTest$test with in function function$inFunctionFunction$1'
        fun inFunctionFunction() {
            logv()
            logd()
            logi()
            logw()
        }
        inFunctionFunction()
        verify { Log.v("prefix_LogvTest", "test_with_in_function_function(inFunctionFunction)") }
        verify { Log.d("prefix_LogvTest", "test_with_in_function_function(inFunctionFunction)") }
        verify { Log.i("prefix_LogvTest", "test_with_in_function_function(inFunctionFunction)") }
        verify { Log.w("prefix_LogvTest", "test_with_in_function_function(inFunctionFunction)") }
    }

    @Test
    @Suppress("RedundantLambdaOrAnonymousFunction")
    fun `test with in function no name lambda`() {
        //class name 'LogsTest$test with in function lambda$1'
        {
            logv()
            logd()
            logi()
            logw()
        }()
        verify { Log.v("prefix_LogvTest", "test with in function no name lambda()") }
        verify { Log.d("prefix_LogvTest", "test with in function no name lambda()") }
        verify { Log.i("prefix_LogvTest", "test with in function no name lambda()") }
        verify { Log.w("prefix_LogvTest", "test with in function no name lambda()") }
    }

    @Test
    fun `test with in class object`() {
        //class name 'LogsTest$inClassObject$1'
        inClassObject.printInClassObjectLog()
        verify { Log.v("prefix_LogvTest", "inClassObject.printInClassObjectLog()") }
        verify { Log.d("prefix_LogvTest", "inClassObject.printInClassObjectLog()") }
        verify { Log.i("prefix_LogvTest", "inClassObject.printInClassObjectLog()") }
        verify { Log.w("prefix_LogvTest", "inClassObject.printInClassObjectLog()") }
    }

    @Test
    fun `out of class function test`() {
        //class name 'LogsTestKt'
        outOfClassFunction()
        verify { Log.v("prefix_LogvTestKt", "outOfClassFunction()") }
        verify { Log.d("prefix_LogvTestKt", "outOfClassFunction()") }
        verify { Log.i("prefix_LogvTestKt", "outOfClassFunction()") }
        verify { Log.w("prefix_LogvTestKt", "outOfClassFunction()") }
    }

    @Test
    fun `test in class runnable` (){
        //class name 'LogvTest', method name 'runnable$lambda-0'
        runnable.run()
        verify { Log.v("prefix_LogvTest", "runnable") }
        verify { Log.d("prefix_LogvTest", "runnable") }
        verify { Log.i("prefix_LogvTest", "runnable") }
        verify { Log.w("prefix_LogvTest", "runnable") }
    }

    @Test
    fun `test in class lambda` (){
        //class name 'LogvTest', method name 'runnable$lambda-0'
        lambda()
        verify { Log.v("prefix_LogvTest", "lambda") }
        verify { Log.d("prefix_LogvTest", "lambda") }
        verify { Log.i("prefix_LogvTest", "lambda") }
        verify { Log.w("prefix_LogvTest", "lambda") }
    }

    @Test
    fun `test in class nested object`() {
        inClassNestedObject.method()
        verify { Log.v("prefix_LogvTest", "nestedObject.printInClassNestedObjectLog()") }
        verify { Log.d("prefix_LogvTest", "nestedObject.printInClassNestedObjectLog()") }
        verify { Log.i("prefix_LogvTest", "nestedObject.printInClassNestedObjectLog()") }
        verify { Log.w("prefix_LogvTest", "nestedObject.printInClassNestedObjectLog()") }
    }

    @Suppress("RedundantLambdaOrAnonymousFunction")
    @Test
    fun `test log with tag`() {
        logv("tag") { "message" }
        logv("tag")
        logd("tag") { "message" }
        logd("tag")
        logi("tag") { "message" }
        logi("tag")
        logw("tag") { "message" }
        logw("tag")
        verify { Log.v("prefix_tag", "test log with tag(): message") }
        verify { Log.v("prefix_tag", "test log with tag()") }
        verify { Log.d("prefix_tag", "test log with tag(): message") }
        verify { Log.d("prefix_tag", "test log with tag()") }
        verify { Log.i("prefix_tag", "test log with tag(): message") }
        verify { Log.i("prefix_tag", "test log with tag()") }
        verify { Log.w("prefix_tag", "test log with tag(): message") }
        verify { Log.w("prefix_tag", "test log with tag()") }
    }
}
