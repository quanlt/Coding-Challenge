package com.quanlt.eventfilter.domain.mapper

import com.quanlt.eventfilter.data.remote.EventResponse
import com.quanlt.eventfilter.domain.Event
import java.util.*
import javax.inject.Inject

class EventMapper @Inject constructor(): Mapper<EventResponse, Event> {

    override fun map(from: EventResponse): Event {
        return Event(
            from.name.orEmpty(),
            from.date ?: Calendar.getInstance().time,
            from.availableSeats ?: 0,
            from.price ?: 0.0,
            from.venue.orEmpty(),
            from.labels.orEmpty()
        )
    }
}