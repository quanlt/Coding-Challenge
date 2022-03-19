package com.quanlt.eventfilter.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quanlt.eventfilter.domain.Event
import com.quanlt.eventfilter.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase
) : ViewModel() {
    private val _state = MutableLiveData(EventsState())
    val state: LiveData<EventsState>
        get() = _state

    fun getEvents() {
        viewModelScope.launch {
            getEventsUseCase(Unit)
                .collect { result -> mapState(_state.value, result) }
        }
    }

    private fun mapState(state: EventsState?, result: Result<List<Event>>) {
        val event = when (result) {
            is Result.OnError -> Fail(result.error, state?.events?.invoke().orEmpty())
            is Result.OnSuccess -> Success(result.getValueOrNull().orEmpty())
            else -> Loading(state?.events?.invoke().orEmpty())
        }
        _state.value = EventsState(event)
    }
}

data class EventsState(val events: Async<List<Event>> = Uninitialized)
