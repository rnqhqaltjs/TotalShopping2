package com.example.totalshopping2.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.totalshopping2.data.model.SearchResponse
import com.example.totalshopping2.data.repository.ItemSearchRepository
import kotlinx.coroutines.launch

class ItemSearchViewModel(private val itemSearchRepository: ItemSearchRepository) : ViewModel() {
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
}