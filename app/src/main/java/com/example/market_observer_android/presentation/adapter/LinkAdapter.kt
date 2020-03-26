package com.example.market_observer_android.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.market_observer_android.R
import com.example.market_observer_android.domain.model.ActiveLink
import kotlinx.android.synthetic.main.item_active_link.view.*


class LinkAdapter(private val listener: LinkAdapterListener) :
    RecyclerView.Adapter<LinkAdapter.LinkHolder>() {

    private lateinit var data: List<ActiveLink>

    fun setData(data: List<ActiveLink>?) {
        if (data != null) {
            this.data = data
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinkHolder {
        return LinkHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_active_link,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: LinkHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size

    inner class LinkHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(link: ActiveLink) {

            itemView.tv_name.text = link.link.name
            itemView.tv_url.text = link.link.url
            itemView.tv_results_count.text = link.results.size.toString()

            itemView.setOnClickListener {
                listener.onLinkClick(link)
            }
            itemView.setOnLongClickListener {
                listener.onLinkLongClick(link)
                true
            }
        }
    }

    interface LinkAdapterListener {

        fun onLinkClick(link: ActiveLink)

        fun onLinkLongClick(link: ActiveLink)
    }
}