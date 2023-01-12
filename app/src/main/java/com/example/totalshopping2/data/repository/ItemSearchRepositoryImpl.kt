package com.example.totalshopping2.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.totalshopping2.data.api.RetrofitInstance.api
import com.example.totalshopping2.data.db.ItemSearchDatabase
import com.example.totalshopping2.data.model.Item
import com.example.totalshopping2.data.model.SearchResponse
import com.example.totalshopping2.data.repository.ItemSearchRepositoryImpl.PreferencesKeys.SORT_MODE
import com.example.totalshopping2.util.Sort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import retrofit2.Response
import java.io.IOException

class ItemSearchRepositoryImpl(
    private val db: ItemSearchDatabase,
    private val dataStore: DataStore<Preferences>
) : ItemSearchRepository {

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

    // DataStore
    private object PreferencesKeys {
        val SORT_MODE = stringPreferencesKey("sort_mode")
    }

    override suspend fun saveSortMode(mode: String) {
        dataStore.edit { prefs ->
            prefs[SORT_MODE] = mode
        }
    }

    override suspend fun getSortMode(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    exception.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { prefs ->
                prefs[SORT_MODE] ?: Sort.SIM.value
            }
    }
}