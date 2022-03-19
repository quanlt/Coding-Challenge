package com.quanlt.eventfilter.utils

import com.quanlt.eventfilter.data.remote.EventResponse
import com.quanlt.eventfilter.data.remote.EventsResponse
import com.quanlt.eventfilter.domain.Event
import java.util.*

object Faker {

    fun makeEventResponse(seed: Int, extraLabel: String = "play") = EventResponse(
        "event response $seed",
        Calendar.getInstance().time,
        seed,
        seed * 100.0,
        "Venue $seed",
        listOf("label $seed", extraLabel)
    )

    fun makeEvent(seed: Int) = Event(
        "event response $seed",
        Calendar.getInstance().time,
        seed,
        seed * 100.0,
        "Venue $seed",
        listOf("label $seed")
    )
}