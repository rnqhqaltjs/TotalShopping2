package com.example.totalshopping2.data.db

import android.content.Context
import androidx.room.*
import com.example.totalshopping2.data.model.Item

@Database(
    entities = [Item::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(OrmConverter::class)
abstract class ItemSearchDatabase : RoomDatabase() {

    abstract fun itemSearchDao(): ItemSearchDao

    companion object {
        @Volatile
        private var INSTANCE: ItemSearchDatabase? = null

        private fun buildDatabase(context: Context): ItemSearchDatabase =
            Room.databaseBuilder(
                context.applicationContext,
                ItemSearchDatabase::class.java,
                "favorite-items"
            ).build()

        fun getInstance(context: Context): ItemSearchDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
    }
}