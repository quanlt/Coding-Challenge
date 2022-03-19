package com.quanlt.eventfilter.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.quanlt.eventfilter.usecase.*
import com.quanlt.eventfilter.utils.Faker.makeEvent
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@ExperimentalCoroutinesApi
class EventsViewModelTest {

    @MockK
    lateinit var getEventsUseCase: GetEventsUseCase

    @InjectMockKs
    lateinit var viewModel: EventsViewModel

    @Before
    fun setUp() = MockKAnnotations.init(this, relaxUnitFun = true)

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private val dispatcher = StandardTestDispatcher()
    val scope = TestScope(dispatcher)

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun getEventSuccessShouldUpdateStateCorrectly() {
        val events = listOf(makeEvent(1), makeEvent(2))
        coEvery { getEventsUseCase.invoke(Unit) } returns flow {
            emit(Result.OnSuccess(events))
        }
        scope.runTest {
            viewModel.getEvents()
            scope.advanceTimeBy(10)
            assertThat(viewModel.state.awaitValue()?.events?.invoke(), CoreMatchers.equalTo(events))
        }
    }

    @Test
    fun getEventFailShouldUpdateStateCorrectly() {
        coEvery { getEventsUseCase.invoke(Unit) } returns flow {
            emit(Result.OnError(Error("error")))
        }
        scope.runTest {
            viewModel.getEvents()
            scope.advanceTimeBy(10)
            val state = viewModel.state.awaitValue()?.events
            assertThat(state, CoreMatchers.instanceOf(Fail::class.java))
        }
    }
}

suspend fun <T> LiveData<T>.awaitValue(): T? {
    return suspendCoroutine { cont ->
        val observer = object : Observer<T> {
            override fun onChanged(t: T?) {
                removeObserver(this)
                cont.resume(t)
            }
        }
        observeForever(observer)
    }
}
