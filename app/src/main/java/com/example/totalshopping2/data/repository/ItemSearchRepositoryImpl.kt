package com.example.totalshopping2.data.repository

import com.example.totalshopping2.data.api.RetrofitInstance.api
import com.example.totalshopping2.data.model.SearchResponse
import retrofit2.Response

class ItemSearchRepositoryImpl : ItemSearchRepository {

    override suspend fun searchItems(
        query: String,
        display: Int,
        sort: String,
        start: Int
    ): Response<SearchResponse> {
        return api.searchItems(query, display, sort, start)
    }
}