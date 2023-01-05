package com.example.totalshopping2.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.totalshopping2.data.repository.ItemSearchRepository

@Suppress("UNCHECKED_CAST")
class ItemSearchViewModelProviderFactory(
    private val bookSearchRepository: ItemSearchRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemSearchViewModel::class.java)) {
            return ItemSearchViewModel(bookSearchRepository) as T
        }
        throw IllegalArgumentException("ViewModel class not found")
    }
}