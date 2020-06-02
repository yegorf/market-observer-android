package com.example.market_observer_android.presentation.view

import android.content.Context
import android.util.AttributeSet
import com.example.market_observer_android.R


class StyledButton : androidx.appcompat.widget.AppCompatButton {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        setTextColor(resources.getColor(R.color.colorWhite))
        setBackgroundResource(R.drawable.bg_button)
    }
}