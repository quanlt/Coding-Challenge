package com.quanlt.eventfilter.domain

import java.util.Date

data class Event(
    val name: String,
    val date: Date,
    val availableSeats: Int,
    val price: Double,
    val venue: String,
    val labels: List<String>
)

internal const val LABEL_PLAY = "play"