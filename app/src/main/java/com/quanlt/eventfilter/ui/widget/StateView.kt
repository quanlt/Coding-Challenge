package com.quanlt.eventfilter.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.quanlt.eventfilter.R
import com.quanlt.eventfilter.databinding.EpxStateViewBinding


@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class StateView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    private val binding = EpxStateViewBinding.inflate(LayoutInflater.from(context), this)

    init {
        orientation = VERTICAL
    }

    @CallbackProp
    @JvmOverloads
    fun setListener(listener: View.OnClickListener? = null) {
        binding.btnRetry.setOnClickListener(listener)
    }

    @TextProp
    fun setMessage(message: CharSequence) {
        binding.tvMessage.text = message
    }

    companion object {
        internal const val ID = "StateView"
    }
}
