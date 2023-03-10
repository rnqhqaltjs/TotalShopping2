package com.example.totalshopping2.data.db

import androidx.paging.PagingSource
import androidx.room.*
import com.example.totalshopping2.data.model.Item
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemSearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(item: Item)

    @Delete
    suspend fun deleteBook(item: Item)

    @Query("SELECT * FROM items")
    fun getFavoriteItems(): Flow<List<Item>>

    @Query("SELECT * FROM items")
    fun getFavoritePagingItems(): PagingSource<Int, Item>
}