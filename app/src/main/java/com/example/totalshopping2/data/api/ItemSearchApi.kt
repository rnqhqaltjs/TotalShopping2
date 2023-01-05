package com.example.totalshopping2.data.api

import com.example.totalshopping2.data.model.SearchResponse
import com.example.totalshopping2.util.Constants.CLIENT_ID
import com.example.totalshopping2.util.Constants.CLIENT_SECRET
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ItemSearchApi {

    @Headers(
        "X-Naver-Client-Id: $CLIENT_ID",
        "X-Naver-Client-Secret: $CLIENT_SECRET"
    )
    @GET("v1/search/shop.json")
    suspend fun searchItems(
        @Query("query") query: String,
        @Query("display") display: Int? = null,
        @Query("sort") sort: String? = null,
        @Query("start") start: Int? = null
    ): Response<SearchResponse>
}