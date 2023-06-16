package com.bangkit.bangkitcapstone.model.data.local.room.workout

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bangkit.bangkitcapstone.model.data.local.entity.WorkoutEntity

@Dao
interface WorkoutDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWorkout(workout: List<WorkoutEntity>)

    @Query("SELECT * FROM WorkoutEntity ORDER BY id DESC")
    suspend fun getAllDailyWorkout(): List<WorkoutEntity>

    @Query("SELECT name FROM WorkoutEntity WHERE id = :id")
    suspend fun getWorkoutName(id: Int): String

}