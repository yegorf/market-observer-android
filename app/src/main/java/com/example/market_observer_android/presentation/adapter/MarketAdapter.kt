package com.example.market_observer_android.presentation.adapter

import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.market_observer_android.R
import com.example.market_observer_android.common.util.Market
import kotlinx.android.synthetic.main.item_market.view.*

class MarketAdapter(private val data: List<Market>) :
    RecyclerView.Adapter<MarketAdapter.MarketHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketHolder {
        return MarketHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_market,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MarketHolder, position: Int) {
        holder.init(data[position])
    }

    inner class MarketHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun init(market: Market) {
            val spannable = SpannableString(market.url)
            spannable.setSpan(UnderlineSpan(), 0, spannable.length, 0)
            itemView.tv_market_url.text = spannable
            itemView.tv_market_name.text = "${market.name} - "
        }
    }
}