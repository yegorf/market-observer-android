package com.example.market_observer_android.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.market_observer_android.R
import com.example.market_observer_android.domain.model.LinkResult

class LinkResultAdapter : RecyclerView.Adapter<LinkResultAdapter.LinkResultHolder>() {

    private lateinit var data: List<LinkResult>

    fun setData(data: List<LinkResult>?) {
        if (data != null) {
            this.data = data
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinkResultHolder {
        return LinkResultHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_link_result,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: LinkResultHolder, position: Int) {
        holder.bind(data[position])
    }

    inner class LinkResultHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(result: LinkResult) {

        }
    }
}