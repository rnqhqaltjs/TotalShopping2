package com.example.totalshopping2.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.totalshopping2.data.model.Item

@Dao
interface ItemSearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(item: Item)

    @Delete
    suspend fun deleteBook(item: Item)

    @Query("SELECT * FROM items")
    fun getFavoriteItems(): LiveData<List<Item>>
}