package com.quanlt.eventfilter.usecase

import com.quanlt.eventfilter.data.CloudRepository
import com.quanlt.eventfilter.data.remote.EventResponse
import com.quanlt.eventfilter.data.remote.EventsResponse
import com.quanlt.eventfilter.domain.Event
import com.quanlt.eventfilter.domain.mapper.EventMapper
import com.quanlt.eventfilter.utils.Faker.makeEventResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class GetEventsUseCaseTest {
    @MockK
    lateinit var repository: CloudRepository

    private val mapper: EventMapper = EventMapper()

    lateinit var usecase: GetEventsUseCase

    @ExperimentalCoroutinesApi
    private val testDispatcher = StandardTestDispatcher()


    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        usecase = GetEventsUseCase(repository, mapper, testDispatcher)
    }

    @Test
    fun whenGetEventsSuccessShouldReturnValue() {
        val events = mutableListOf<EventResponse>()
        events.add(makeEventResponse(0));
        events.add(makeEventResponse(4));
        events.add(makeEventResponse(3));
        events.add(makeEventResponse(2));
        events.add(makeEventResponse(1, "not play"));
        val response = EventsResponse(events)
        coEvery { repository.getEvents() } returns response
        runTest(testDispatcher) {
            val result = usecase.invoke(Unit).toList()
            assertThat(result.size, equalTo(2))
            assertThat(result[0], instanceOf(Result.OnLoading.javaClass))
            val actualEvents = result[1] as Result.OnSuccess<List<Event>>
            assertThat(actualEvents.data.size, equalTo(3))
        }
    }

    @Test
    fun whenGetEventsSuccessShouldReturnError() {
        val events = mutableListOf<EventResponse>()
        events.add(makeEventResponse(0));
        events.add(makeEventResponse(3));
        events.add(makeEventResponse(2));
        events.add(makeEventResponse(1));
        val response = EventsResponse(events)
        coEvery { repository.getEvents() } throws NullPointerException()
        runTest(testDispatcher) {
            val result = usecase.invoke(Unit).toList()
            assertThat(result.size, equalTo(2))
            assertThat(result[0], instanceOf(Result.OnLoading.javaClass))
            assertThat(result[1], instanceOf(Result.OnError::class.java))
        }
    }

}