package com.payback.commons

@Suppress("TooGenericExceptionCaught") // expected
internal fun <T> Result<T>.mapFailure(transform: (Throwable) -> Result<T>): Result<T> {
    return if (this.isFailure) {
        try {
            transform(this.exceptionOrNull() ?: IllegalStateException("$this"))
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    } else {
        this
    }
}
