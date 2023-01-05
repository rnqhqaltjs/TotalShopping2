package com.example.totalshopping2.ui.adapter

import android.text.Html
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.totalshopping2.data.model.Item
import com.example.totalshopping2.databinding.ItemPreviewBinding
import java.text.DecimalFormat

class ItemSearchViewHolder(
    private val binding: ItemPreviewBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Item) {
        val title = item.title
        val dec = DecimalFormat("###,###")

        itemView.apply {
            binding.ivArticleImage.load(item.image)
            binding.tvTitle.text = Html.fromHtml(title).toString()
            binding.tvLprice.text = item.lprice
            binding.tvCategory1.text = item.category1
        }
    }
}