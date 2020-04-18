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

    private var data = listOf<ActiveLink>()

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

            if (link.results.isNotEmpty()) {
                itemView.v_circle.visibility = View.VISIBLE
                itemView.v_circle.setCount(link.results.size)
            } else {
                itemView.v_circle.visibility = View.GONE
            }

            itemView.setOnClickListener {
                listener.onLinkClick(link)
            }
            itemView.setOnLongClickListener {
                listener.onLinkLongClick(link)
                true
            }

            setEyeImage(link.link.isActive)
            itemView.btn_activate_link.setOnClickListener {
                link.link.isActive = !link.link.isActive
                listener.onEyeClick(link)
            }
        }

        private fun setEyeImage(isActive: Boolean) {
            if (isActive) {
                itemView.btn_activate_link.setImageResource(R.drawable.ic_eye_opened)
            } else {
                itemView.btn_activate_link.setImageResource(R.drawable.ic_eye_closed)
            }
        }
    }

    interface LinkAdapterListener {

        fun onLinkClick(link: ActiveLink)

        fun onLinkLongClick(link: ActiveLink)

        fun onEyeClick(link: ActiveLink)
    }
}