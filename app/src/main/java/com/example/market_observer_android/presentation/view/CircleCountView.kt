package com.example.market_observer_android.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.market_observer_android.R
import kotlinx.android.synthetic.main.count_circle_view.view.*

class CircleCountView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        initializeViews(context)
    }

    private fun initializeViews(context: Context?) {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.count_circle_view, this)
    }

    fun setCount(count: Int) {
        val text: String
        val textSize: Int
        when {
            count >= 100 -> {
                text = "99+"
                textSize = 10
            }
            count >= 10 -> {
                text = count.toString()
                textSize = 12
            }
            else -> {
                text = count.toString()
                textSize = 14
            }
        }
        tv_count.textSize = textSize.toFloat()
        tv_count.text = text
    }
}