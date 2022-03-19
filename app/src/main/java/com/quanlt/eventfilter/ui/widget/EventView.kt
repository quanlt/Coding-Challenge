package com.quanlt.eventfilter.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.quanlt.eventfilter.R
import com.quanlt.eventfilter.databinding.EpxEventViewBinding
import com.quanlt.eventfilter.domain.Event

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class EventView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    style: Int = 0
) : LinearLayout(context, attributeSet, style) {

    private var binding =
        EpxEventViewBinding.inflate(LayoutInflater.from(context), this)

    init {
        orientation  = VERTICAL
    }

    @ModelProp
    fun setEvent(event: Event) {
        binding.tvName.text = context.getString(R.string.label_name, event.name)
        binding.tvPrice.text = context.getString(R.string.label_price, event.price.toString())
        binding.tvVenue.text = context.getString(R.string.label_price, event.venue)
    }


    companion object {
        internal const val ID = "EventView"
    }
}