package com.bangkit.bangkitcapstone.model.data.local.room.workout

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bangkit.bangkitcapstone.model.data.local.entity.WorkoutEntity


@Database(
    entities = [WorkoutEntity::class],
    version = 1,
    exportSchema = false
)
abstract class WorkoutRoomDatabase : RoomDatabase() {

    abstract fun workoutDao(): WorkoutDao

    companion object {
        @Volatile
        private var INSTANCE: WorkoutRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): WorkoutRoomDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    WorkoutRoomDatabase::class.java, "workout.db"
                ).build()
            }
    }

}