package com.quanlt.eventfilter.usecase

sealed class Result<out T> {
    fun getValueOrNull(): T? {
        return if (this is OnSuccess) this.data else null
    }

    data class OnSuccess<out T>(val data: T) : Result<T>()

    data class OnError<out T>(val error: Error) : Result<T>()

    object OnLoading : Result<Nothing>()

    object Uninitialized : Result<Nothing>()
}

open class Error(
    val message: String? = null,
    val cause: Throwable? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Error

        if (message != other.message) return false
        if (cause != other.cause) return false

        return true
    }

    override fun hashCode(): Int {
        var result = message?.hashCode() ?: 0
        result = 31 * result + (cause?.hashCode() ?: 0)
        return result
    }
}

class ErrorThrowable(
    override val message: String? = null,
    override val cause: Throwable? = null
) : Throwable(message) {
    fun toError(): Error =
        Error(message, cause)
}

/**
 * A convenient method for wrapping a [Throwable] in a[Result].
 * Converts a [Throwable] to a [Result.OnError].
 */
fun <T> Throwable.toResult(): Result<T> {
    return when (this) {
        is ErrorThrowable -> Result.OnError(this.toError())
        else -> Result.OnError(UnknownError(cause = this))
    }
}

class UnknownError(message: String? = null, cause: Throwable? = null) :
    Error(message ?: "Unknown error", cause)

fun <T> T.toResult(): Result<T> {
    return Result.OnSuccess(this)
}
