package com.bangkit.bangkitcapstone.model.data.local.room.food

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bangkit.bangkitcapstone.model.data.local.entity.FoodEntity

@Dao
interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFood(food: List<FoodEntity>)

    @Query("SELECT * FROM FoodEntity ORDER BY id DESC")
    fun getAllFood(): LiveData<List<FoodEntity>>

}