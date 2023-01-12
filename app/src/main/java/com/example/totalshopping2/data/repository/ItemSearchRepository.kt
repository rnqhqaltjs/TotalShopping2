package com.example.totalshopping2.data.repository

import com.example.totalshopping2.data.model.Item
import com.example.totalshopping2.data.model.SearchResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface ItemSearchRepository {

    suspend fun searchItems(
        query: String,
        display: Int,
        sort: String,
        start: Int
    ): Response<SearchResponse>

    //Room
    suspend fun insertItems(item: Item)

    suspend fun deleteItems(item: Item)

    fun getFavoriteItems(): Flow<List<Item>>
}