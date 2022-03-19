package com.quanlt.eventfilter.ui.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ProgressBar
import com.airbnb.epoxy.ModelView
import com.quanlt.eventfilter.R
import com.quanlt.eventfilter.databinding.EpxLoadingViewBinding

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_MATCH_HEIGHT)
class LoaderView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    style: Int = 0
) : FrameLayout(
    context,
    attributeSet,
    style
) {

    init {
        EpxLoadingViewBinding.inflate(LayoutInflater.from(context), this)
    }

    companion object {
        internal const val ID = "LoaderView"
    }
}