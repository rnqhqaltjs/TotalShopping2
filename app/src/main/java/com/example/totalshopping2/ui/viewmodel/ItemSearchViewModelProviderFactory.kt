package com.example.totalshopping2.ui.viewmodel

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.example.totalshopping2.data.repository.ItemSearchRepository

@Suppress("UNCHECKED_CAST")
//class ItemSearchViewModelProviderFactory(
//    private val bookSearchRepository: ItemSearchRepository
//) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(ItemSearchViewModel::class.java)) {
//            return ItemSearchViewModel(bookSearchRepository) as T
//        }
//        throw IllegalArgumentException("ViewModel class not found")
//    }
//}
class ItemSearchViewModelProviderFactory(
    private val bookSearchRepository: ItemSearchRepository,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        if (modelClass.isAssignableFrom(ItemSearchViewModel::class.java)) {
            return ItemSearchViewModel(bookSearchRepository, handle) as T
        }
        throw IllegalArgumentException("ViewModel class not found")
    }
}