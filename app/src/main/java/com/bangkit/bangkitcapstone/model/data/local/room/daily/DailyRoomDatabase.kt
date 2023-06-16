package com.bangkit.bangkitcapstone.model.data.local.room.daily

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bangkit.bangkitcapstone.model.data.local.entity.CaloriesDailyEntity

@Database(
    entities = [CaloriesDailyEntity::class],
    version = 3,
    autoMigrations = [
        AutoMigration(from = 2, to = 3)
    ],
    exportSchema = true
)
abstract class DailyRoomDatabase : RoomDatabase() {

    abstract fun dailyDao(): DailyDao

    companion object {
        @Volatile
        private var INSTANCE: DailyRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): DailyRoomDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    DailyRoomDatabase::class.java, "daily.db"
                )
                    .addMigrations(MIGRATION_2_3)
                    .build()
            }

        private val MIGRATION_2_3: Migration = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE CaloriesDailyEntity ADD COLUMN duration TEXT")
            }
        }
    }
}