package com.quanlt.eventfilter.ui

import com.airbnb.epoxy.EpoxyController
import com.quanlt.eventfilter.domain.Event
import com.quanlt.eventfilter.ui.widget.*
import com.quanlt.eventfilter.usecase.*

class EventController(private val viewModel: EventsViewModel) : EpoxyController() {
    var result: Async<List<Event>> = Uninitialized
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        val viewModel = viewModel;
        when (val events = result) {
            is Fail -> stateView {
                id(StateView.ID)
                message(events.error.message.orEmpty())
                listener { _ -> viewModel.getEvents() }
            }
            is Success ->
                events.invoke().forEachIndexed { index, event ->
                    eventView {
                        id(EventView.ID, index.toString())
                        event(event)
                    }
                }
            else -> loaderView {
                id(LoaderView.ID)
            }
        }
    }

}