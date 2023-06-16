package com.bangkit.bangkitcapstone.model.data.local.room.food

import android.content.Context
import androidx.room.*
import com.bangkit.bangkitcapstone.model.data.local.entity.FoodEntity

@Database(
    entities = [FoodEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(FoodHealthConverter::class)
abstract class FoodRoomDatabase : RoomDatabase() {

    abstract fun foodDao(): FoodDao

    companion object {
        @Volatile
        private var INSTANCE: FoodRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): FoodRoomDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    FoodRoomDatabase::class.java, "food.db"
                )
                    .fallbackToDestructiveMigrationOnDowngrade()
                    .build()
            }
    }

}