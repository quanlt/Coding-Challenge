package com.quanlt.eventfilter.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

abstract class UseCase<in P, out R>(
) {

    operator fun invoke(params: P): Flow<Result<R>> {
        return flow {
            emit(Result.OnLoading)
            emit(Result.OnSuccess(doWork(params)))
        }
            .catch { e -> emit(parseError(e)) }
    }

    suspend fun executeSync(params: P) = doWork(params)

    protected abstract suspend fun doWork(params: P): R

    protected open fun parseError(throwable: Throwable): Result.OnError<R> {
        return Result.OnError(Error(throwable.message.orEmpty(), throwable))
    }
}
