package com.efficient.logs

fun CharSequence?.logNonNull(): CharSequence = NonNull(this)
fun CharSequence?.logNonEmpty(): CharSequence = NonEmpty(this)

class NonNull(text: CharSequence?) : LogCharSequence(text)
class NonEmpty(text: CharSequence?) : LogCharSequence(text)
abstract class LogCharSequence(val text: CharSequence?) : CharSequence {
    override val length: Int get() = text?.length ?: 0
    override fun get(index: Int): Char = text?.get(index) ?: throw NullPointerException()
    override fun toString(): String = text.toString()
    override fun subSequence(startIndex: Int, endIndex: Int): CharSequence {
        return text?.subSequence(startIndex, endIndex) ?: throw NullPointerException()
    }
}