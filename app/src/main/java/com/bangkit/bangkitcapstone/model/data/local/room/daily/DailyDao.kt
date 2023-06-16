package com.bangkit.bangkitcapstone.model.data.local.room.daily

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bangkit.bangkitcapstone.model.data.local.IntakeType
import com.bangkit.bangkitcapstone.model.data.local.entity.CaloriesDailyEntity

@Dao
interface DailyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDaily(daily: CaloriesDailyEntity)

    @Query("SELECT intake FROM CaloriesDailyEntity WHERE type = :intakeType AND date = :date")
    suspend fun getIntakeData(intakeType: IntakeType, date: String): List<String>

    @Query("SELECT * FROM CaloriesDailyEntity WHERE type != 'WORKOUT' ORDER BY date DESC")
    suspend fun getAllDaily(): List<CaloriesDailyEntity>

    @Query("SELECT * FROM CaloriesDailyEntity WHERE type == 'WORKOUT' ORDER BY date DESC")
    suspend fun getAllDailyWorkout(): List<CaloriesDailyEntity>

    @Query("DELETE FROM CaloriesDailyEntity")
    suspend fun deleteAll()
}