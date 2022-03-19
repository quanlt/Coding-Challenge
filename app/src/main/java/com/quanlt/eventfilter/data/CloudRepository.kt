package com.quanlt.eventfilter.data

import com.quanlt.eventfilter.data.remote.CloudService
import javax.inject.Inject

class CloudRepository @Inject constructor(private val service: CloudService) {
    suspend fun getEvents() = service.getEvents()
}