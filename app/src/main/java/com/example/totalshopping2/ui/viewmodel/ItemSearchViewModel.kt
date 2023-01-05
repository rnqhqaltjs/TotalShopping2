package com.example.totalshopping2.ui.viewmodel

import androidx.lifecycle.*
import com.example.totalshopping2.data.model.SearchResponse
import com.example.totalshopping2.data.repository.ItemSearchRepository
import kotlinx.coroutines.launch

class ItemSearchViewModel(
    private val itemSearchRepository: ItemSearchRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _searchResult = MutableLiveData<SearchResponse>()
    val searchResult: LiveData<SearchResponse> get() = _searchResult

    fun searchItems(query: String) = viewModelScope.launch {
        val response = itemSearchRepository.searchItems(query, 10, "sim", 1)
        if (response.isSuccessful) {
            response.body()?.let { body ->
                _searchResult.postValue(body)
            }
        }
    }

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