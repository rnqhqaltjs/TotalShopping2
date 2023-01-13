package com.example.totalshopping2.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.totalshopping2.data.model.Item
import com.example.totalshopping2.databinding.ItemPreviewBinding

class ItemSearchPagingAdapter : PagingDataAdapter<Item, ItemSearchViewHolder>(ItemDiffCallback) {

    override fun onBindViewHolder(holder: ItemSearchViewHolder, position: Int) {
        val pagedItem = getItem(position)
        pagedItem?.let { item ->
            holder.bind(item)
            holder.itemView.setOnClickListener {
                onItemClickListener?.let { it(item) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemSearchViewHolder {
        return ItemSearchViewHolder(
            ItemPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    private var onItemClickListener: ((Item) -> Unit)? = null
    fun setOnItemClickListener(listener: (Item) -> Unit) {
        onItemClickListener = listener
    }

    companion object {
        private val ItemDiffCallback = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem.productId == newItem.productId
            }

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem == newItem
            }
        }
    }
}