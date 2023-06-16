package com.bangkit.bangkitcapstone.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.bangkit.bangkitcapstone.model.data.AppRepository
import com.bangkit.bangkitcapstone.model.data.local.room.daily.DailyRoomDatabase
import com.bangkit.bangkitcapstone.model.data.local.room.food.FoodRoomDatabase
import com.bangkit.bangkitcapstone.model.data.local.room.workout.WorkoutRoomDatabase
import com.bangkit.bangkitcapstone.model.data.local.store.DataStoreManager
import com.bangkit.bangkitcapstone.model.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

private const val USER_PREFERENCES = "user_preferences"

object Injection {

    fun provideRepository(context: Context): AppRepository {
        val apiService = ApiConfig.getApiService()
        val databaseDaily = DailyRoomDatabase.getDatabase(context)
        val databaseWorkout = WorkoutRoomDatabase.getDatabase(context)
        val foodRoomDatabase = FoodRoomDatabase.getDatabase(context)
        val dailyDao = databaseDaily.dailyDao()
        val workoutDao = databaseWorkout.workoutDao()
        val foodDao = foodRoomDatabase.foodDao()
        return AppRepository.getInstance(
            DataStoreManager.getInstance(providePreferencesDataStore(context)),
            apiService,
            dailyDao,
            workoutDao,
            foodDao
        )
    }

    private fun providePreferencesDataStore(appContext: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            migrations = listOf(SharedPreferencesMigration(appContext, USER_PREFERENCES)),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { appContext.preferencesDataStoreFile(USER_PREFERENCES) }
        )
    }

}