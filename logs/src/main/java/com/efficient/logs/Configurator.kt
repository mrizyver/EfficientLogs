package com.efficient.logs

object Configurator {
    fun nonnull(text: CharSequence?): CharSequence = NonNull(text)
    fun nonempty(text: CharSequence?): CharSequence = NonEmpty(text)

    class NonNull(text: CharSequence?) : LogCharSequence(text)
    class NonEmpty(text: CharSequence?) : LogCharSequence(text)
    abstract class LogCharSequence(val text: CharSequence?) : CharSequence {
        override val length: Int  get() = text?.length ?: throw NullPointerException()
        override fun get(index: Int): Char = text?.get(index) ?: throw NullPointerException()
        override fun subSequence(startIndex: Int, endIndex: Int): CharSequence {
            return text?.subSequence(startIndex, endIndex) ?: throw NullPointerException()
        }
    }
}