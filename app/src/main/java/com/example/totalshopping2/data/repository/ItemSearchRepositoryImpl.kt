package com.example.totalshopping2.data.repository

import com.example.totalshopping2.data.api.RetrofitInstance.api
import com.example.totalshopping2.data.db.ItemSearchDatabase
import com.example.totalshopping2.data.model.Item
import com.example.totalshopping2.data.model.SearchResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class ItemSearchRepositoryImpl(private val db: ItemSearchDatabase) : ItemSearchRepository {

    override suspend fun searchItems(
        query: String,
        display: Int,
        sort: String,
        start: Int
    ): Response<SearchResponse> {
        return api.searchItems(query, display, sort, start)
    }

    override suspend fun insertItems(item: Item) {
        db.itemSearchDao().insertBook(item)
    }

    override suspend fun deleteItems(item: Item) {
        db.itemSearchDao().deleteBook(item)
    }

    override fun getFavoriteItems(): Flow<List<Item>> {
        return db.itemSearchDao().getFavoriteItems()
    }
}