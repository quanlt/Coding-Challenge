package com.quanlt.eventfilter.usecase

import com.quanlt.eventfilter.data.CloudRepository
import com.quanlt.eventfilter.di.IoDispatcher
import com.quanlt.eventfilter.domain.Event
import com.quanlt.eventfilter.domain.LABEL_PLAY
import com.quanlt.eventfilter.domain.mapper.EventMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetEventsUseCase @Inject constructor(
    private val repository: CloudRepository,
    private val mapper: EventMapper,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : UseCase<Unit, List<Event>>() {
    override suspend fun doWork(params: Unit): List<Event> {
        return withContext(dispatcher) {
            repository.getEvents().events.orEmpty()
                .map { mapper.map(it) }
                .filter { it.availableSeats > 0 }
                .filter { it.labels.contains(LABEL_PLAY) }
                .sortedBy { it.date }
        }
    }
}