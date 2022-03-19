package com.quanlt.eventfilter.data.remote

import retrofit2.http.GET

interface CloudService {
    @GET("bridj-coding-challenge/events.json")
    suspend fun getEvents(): EventsResponse
}