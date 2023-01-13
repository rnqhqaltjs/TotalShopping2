package com.example.totalshopping2.ui.viewmodel

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.totalshopping2.data.model.Item
import com.example.totalshopping2.data.model.SearchResponse
import com.example.totalshopping2.data.repository.ItemSearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ItemSearchViewModel(
    private val itemSearchRepository: ItemSearchRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _searchResult = MutableLiveData<SearchResponse>()
    val searchResult: LiveData<SearchResponse> get() = _searchResult

    fun searchItems(query: String) = viewModelScope.launch(Dispatchers.IO) {
        val response = itemSearchRepository.searchItems(query, 15, getSortMode(), 1)
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

    // DataStore
    fun saveSortMode(value: String) = viewModelScope.launch(Dispatchers.IO) {
        itemSearchRepository.saveSortMode(value)
    }

    suspend fun getSortMode() = withContext(Dispatchers.IO) {
        itemSearchRepository.getSortMode().first()
    }

    // Paging
    val favoritePagingItems: StateFlow<PagingData<Item>> =
        itemSearchRepository.getFavoritePagingItems()
            .cachedIn(viewModelScope)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PagingData.empty())

    private val _searchPagingResult = MutableStateFlow<PagingData<Item>>(PagingData.empty())
    val searchPagingResult: StateFlow<PagingData<Item>> = _searchPagingResult.asStateFlow()

    fun searchItemsPaging(query: String) {
        viewModelScope.launch {
            itemSearchRepository.searchItemsPaging(query, getSortMode())
                .cachedIn(viewModelScope)
                .collect {
                    _searchPagingResult.value = it
                }
        }
    }
}