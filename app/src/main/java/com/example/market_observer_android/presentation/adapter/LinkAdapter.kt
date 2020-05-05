package com.example.market_observer_android.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.market_observer_android.R
import com.example.market_observer_android.domain.model.Link
import com.example.market_observer_android.presentation.view.CircleCountView
import kotlinx.android.synthetic.main.item_active_link.view.*


class LinkAdapter(private val listener: LinkAdapterListener) :
    RecyclerView.Adapter<LinkAdapter.LinkHolder>() {

    private var data = listOf<Link>()

    fun setData(data: List<Link>?) {
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

        private lateinit var nameText: TextView
        private lateinit var urlText: TextView
        private lateinit var activateButton: ImageView
        private lateinit var countView: CircleCountView

        private fun initViews() {
            nameText = itemView.tv_name
            urlText = itemView.tv_url
            activateButton = itemView.btn_activate_link
            countView = itemView.v_circle
        }

        fun bind(link: Link) {
            initViews()
            nameText.text = link.name
            urlText.text = link.url

            setCircleCount(link)

            itemView.setOnClickListener {
                listener.onLinkClick(link)
            }
            itemView.setOnLongClickListener {
                listener.onLinkLongClick(link)
                true
            }

            setEyeImage(link.isActive)
            activateButton.setOnClickListener {
                link.isActive = !link.isActive
                listener.onEyeClick(link)
            }
        }

        private fun setCircleCount(link: Link) {
            if (link.results.isNotEmpty()) {
                val newResults = link.results.filter { !it.isViewed }
                countView.visibility = View.VISIBLE
                countView.setCount(newResults.size)
            } else {
                countView.visibility = View.GONE
            }
        }

        private fun setEyeImage(isActive: Boolean) {
            if (isActive) {
                activateButton.setImageResource(R.drawable.ic_eye_opened)
            } else {
                activateButton.setImageResource(R.drawable.ic_eye_closed)
            }
        }
    }

    interface LinkAdapterListener {

        fun onLinkClick(link: Link)

        fun onLinkLongClick(link: Link)

        fun onEyeClick(link: Link)
    }
}