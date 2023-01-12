package com.example.totalshopping2.ui.viewmodel

import androidx.lifecycle.*
import com.example.totalshopping2.data.model.Item
import com.example.totalshopping2.data.model.SearchResponse
import com.example.totalshopping2.data.repository.ItemSearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ItemSearchViewModel(
    private val itemSearchRepository: ItemSearchRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _searchResult = MutableLiveData<SearchResponse>()
    val searchResult: LiveData<SearchResponse> get() = _searchResult

    fun searchItems(query: String) = viewModelScope.launch(Dispatchers.IO) {
        val response = itemSearchRepository.searchItems(query, 10, "sim", 1)
        if (response.isSuccessful) {
            response.body()?.let { body ->
                _searchResult.postValue(body)
            }
        }
    }

    //Room
    fun saveItem(item: Item) = viewModelScope.launch(Dispatchers.IO) {
        itemSearchRepository.insertItems(item)
    }

    fun deleteItem(item: Item) = viewModelScope.launch(Dispatchers.IO) {
        itemSearchRepository.deleteItems(item)
    }

    //    val favoriteItems: Flow<List<Item>> = itemSearchRepository.getFavoriteItems()
    val favoriteItems: StateFlow<List<Item>> = itemSearchRepository.getFavoriteItems()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf())

    // SavedState
    var query = String()
        set(value) {
            field = value
            savedStateHandle[SAVE_STATE_KEY] = value
        }

    init {
        query = savedStateHandle.get<String>(SAVE_STATE_KEY) ?: ""
    }

    companion object {
        private const val SAVE_STATE_KEY = "query"
    }
}