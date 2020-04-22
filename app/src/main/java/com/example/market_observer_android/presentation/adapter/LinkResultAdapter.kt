package com.example.market_observer_android.presentation.adapter

import android.view.*
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.market_observer_android.R
import com.example.market_observer_android.domain.model.LinkResult
import kotlinx.android.synthetic.main.item_link_result.view.*


class LinkResultAdapter(private val listener: LinkResultListener) :
    RecyclerView.Adapter<LinkResultAdapter.LinkResultHolder>() {

    private var data: List<LinkResult> = ArrayList()

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
            itemView.tv_title.text = result.title
            if (result.imageUrl != null) {
                Glide.with(itemView)
                    .load(result.imageUrl)
                    .into(itemView.iv_image)
            }

            itemView.setOnClickListener {
                if (result.url != null) {
                    listener.onResultClick(result)
                }
            }

            itemView.tv_location.text = result.location
            itemView.tv_price.text = result.price

            itemView.iv_result_menu.setOnClickListener {
                val popup = PopupMenu(itemView.context, itemView.iv_result_menu)
                popup.menuInflater.inflate(R.menu.found_result_context_menu, popup.menu)
                popup.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.save -> {
                            listener.onResultSave(result)
                            true
                        }
                        else -> false
                    }
                }
                popup.show()
            }
        }
    }

    interface LinkResultListener {

        fun onResultClick(result: LinkResult)

        fun onResultSave(result: LinkResult)
    }
}