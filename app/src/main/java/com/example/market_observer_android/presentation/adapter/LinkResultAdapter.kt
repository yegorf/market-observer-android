package com.example.market_observer_android.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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

        private lateinit var titleText: TextView
        private lateinit var locationText: TextView
        private lateinit var priceText: TextView
        private lateinit var saveBtn: ImageView
        private lateinit var image: ImageView

        private fun initViews() {
            titleText = itemView.tv_title
            locationText = itemView.tv_location
            priceText = itemView.tv_price
            saveBtn = itemView.iv_result_menu
            image = itemView.iv_image
        }

        fun bind(result: LinkResult) {
            initViews()
            titleText.text = result.title
            if (result.imageUrl != null) {
                Glide.with(itemView)
                    .load(result.imageUrl)
                    .into(image)
            }

            itemView.setOnClickListener {
                if (result.url != null) {
                    listener.onResultClick(result)
                }
            }

            locationText.text = result.location
            priceText.text = result.price

            if (result.isSaved) {
                saveBtn.setImageResource(R.drawable.devele_fav)
            } else {
                saveBtn.setImageResource(R.drawable.add_fav)
            }
            saveBtn.setOnClickListener {
                if (result.isSaved) {
                    listener.onResultUnsave(result)
                } else {
                    listener.onResultSave(result)
                }
                result.isSaved = !result.isSaved
                notifyItemChanged(adapterPosition)
            }
        }
    }

    interface LinkResultListener {

        fun onResultClick(result: LinkResult)

        fun onResultSave(result: LinkResult)

        fun onResultUnsave(result: LinkResult)
    }
}