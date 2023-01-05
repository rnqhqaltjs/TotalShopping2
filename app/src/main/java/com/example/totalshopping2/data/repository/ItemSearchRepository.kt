package com.example.totalshopping2.data.repository

import com.example.totalshopping2.data.model.SearchResponse
import retrofit2.Response

interface ItemSearchRepository {

    suspend fun searchItems(
        query: String,
        display: Int,
        sort: String,
        start: Int
    ): Response<SearchResponse>
}