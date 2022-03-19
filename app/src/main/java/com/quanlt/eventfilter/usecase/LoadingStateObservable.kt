package com.quanlt.eventfilter.usecase


sealed class Async<out T>(val complete: Boolean, val shouldLoad: Boolean, private val value: T?) {

    open operator fun invoke(): T? = value

    companion object {

        fun <T> Success<*>.setMetadata(metadata: T) {
            this.metadata = metadata
        }

        @Suppress("UNCHECKED_CAST")
        fun <T> Success<*>.getMetadata(): T? = this.metadata as T?
    }
}

object Uninitialized : Async<Nothing>(complete = false, shouldLoad = true, value = null), Incomplete

data class Loading<out T>(private val value: T? = null) :
    Async<T>(complete = false, shouldLoad = false, value = value), Incomplete

data class Success<out T>(private val value: T) :
    Async<T>(complete = true, shouldLoad = false, value = value) {

    override operator fun invoke(): T = value
    var metadata: Any? = null
}

data class Fail<out T>(val error: Error, private val value: T? = null) :
    Async<T>(complete = true, shouldLoad = true, value = value) {
    override fun equals(other: Any?): Boolean {
        if (other !is Fail<*>) return false

        val otherError = other.error
        return error::class == otherError::class &&
            error.message == otherError.message &&
            error.cause?.stackTrace?.firstOrNull() == otherError.cause?.stackTrace?.firstOrNull()
    }

    override fun hashCode(): Int =
        arrayOf(
            error::class, error.message,
            error.cause?.stackTrace?.firstOrNull()
        ).contentHashCode()
}

/**
 * Helper interface for using Async in a when clause for handling both Uninitialized and Loading.
 *
 * With this, you can do:
 * when (data) {
 *     is Incomplete -> Unit
 *     is Success    -> Unit
 *     is Fail       -> Unit
 * }
 */
interface Incomplete
