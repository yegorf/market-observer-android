package com.example.market_observer_android.presentation.view

import android.content.Context
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.market_observer_android.R
import kotlinx.android.synthetic.main.styled_edit_text_view.view.*


class StyledEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.styled_edit_text_view, this)
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.StyledEditText,
            0, 0
        )
        val title = a.getString(R.styleable.StyledEditText_title)
        view.styled_et_title.text = title
    }

    fun getText() = styled_et_input.text.toString()

    fun setText(text: String) {
        styled_et_input.setText(text)
    }

    fun addTextChangeListener(watcher: TextWatcher) {
        styled_et_input.addTextChangedListener(watcher)
    }
}