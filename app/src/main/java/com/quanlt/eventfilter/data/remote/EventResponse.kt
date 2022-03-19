package com.quanlt.eventfilter.data.remote

import com.google.gson.annotations.SerializedName
import java.util.Date

data class EventResponse(
    @SerializedName("name")
    val name: String?,
    @SerializedName("date")
    val date: Date?,
    @SerializedName("available_seats")
    val availableSeats: Int?,
    @SerializedName("price")
    val price: Double?,
    @SerializedName("venue")
    val venue: String?,
    @SerializedName("labels")
    val labels: List<String>?
)

data class EventsResponse(
    @SerializedName("events")
    val events: List<EventResponse>?
)