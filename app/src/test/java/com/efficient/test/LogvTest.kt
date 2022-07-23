package com.efficient.test

import android.util.Log
import com.efficient.logs.logv
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.Before
import org.junit.Test

fun outOfClassFunction() {
    logv()
}

internal class LogvTest {

    private class LogHolder {
        fun printLog() {
            logv()
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
        }
    }

    private val runnable = Runnable { logv() }

    @Before
    fun before() {
        mockkStatic(Log::class)
    }

    @Test
    fun `test with enclosing brackets`() {
        //class name 'LogsTest'
        logv()
        verify { Log.v("LogvTest", "test with enclosing brackets()") }
    }

    @Test
    fun `test in static class inside other class`() {
        //class name 'LogsTest$LogHolder'
        LogHolder().printLog()
        verify { Log.v("LogHolder", "printLog()") }
    }

    @Test
    fun `test in lambda invoker`() {
        //class name 'LogsTest$test in lambda invoker$1'
        LambdaInvoker().invokeLambda { logv() }
        verify { Log.v("LogvTest", "test in lambda invoker()") }
    }

    @Test
    fun `test in lambda holder`() {
        //class name 'LogsTest$test in lambda holder$1'
        val holder = LambdaHolder()
        holder.setLambda { logv() }
        holder.invokeLambda()
        verify { Log.v("LogvTest", "test in lambda holder()") }
    }

    @Test
    fun `test with in function class`() {
        //class name 'LogsTest$test with in function class$InFunctionClass'
        class InFunctionClass {
            fun printInFunctionLog() {
                logv()
            }
        }
        InFunctionClass().printInFunctionLog()
        verify { Log.v("LogvTest", "test with in function class(printInFunctionLog)") }
    }

    @Test
    fun `test with in function object`() {
        //class name 'LogsTest$test with in function object$inFunctionObject$1'
        val inFunctionObject = object {
            fun printInFunctionObjectLog() {
                logv()
            }
        }
        inFunctionObject.printInFunctionObjectLog()
        verify { Log.v("LogvTest", "test with in function object(printInFunctionObjectLog)") }
    }

    @Test
    fun `test with in function object and function invoke`() {
        //class name 'LogsTest$test with in function object$inFunctionObject$1'
        val inFunctionObject = object {
            fun invoke() {
                logv()
            }
        }
        inFunctionObject.invoke()
        verify { Log.v("LogvTest", "test with in function object and function invoke(inFunctionObject)") }
    }

    @Test
    fun `test with in function lambda`() {
        //class name 'LogsTest$test with in function lambda$inFunctionLambda$1'
        val inFunctionLambda = { logv() }
        inFunctionLambda()
        verify { Log.v("LogvTest", "test with in function lambda(inFunctionLambda)") }
    }

    @Test
    fun `test with in function function`() {
        //class name 'LogsTest$test with in function function$inFunctionFunction$1'
        fun inFunctionFunction() {
            logv()
        }
        inFunctionFunction()
        verify { Log.v("LogvTest", "test_with_in_function_function(inFunctionFunction)") }
    }

    @Test
    fun `test with in function no name lambda`() {
        //class name 'LogsTest$test with in function lambda$1'
        { logv() }()
        verify { Log.v("LogvTest", "test with in function no name lambda()") }
    }

    @Test
    fun `test with in class object`() {
        //class name 'LogsTest$inClassObject$1'
        inClassObject.printInClassObjectLog()
        verify { Log.v("LogvTest", "inClassObject.printInClassObjectLog()") }
    }

    @Test
    fun `out of class function test`() {
        //class name 'LogsTestKt'
        outOfClassFunction()
        verify { Log.v("LogvTestKt", "outOfClassFunction()") }
    }

    @Test
    fun `test in class runnable` (){
        //class name 'LogvTest', method name 'runnable$lambda-0'
        runnable.run()
        verify { Log.v("LogvTest", "runnable") }
    }
}
