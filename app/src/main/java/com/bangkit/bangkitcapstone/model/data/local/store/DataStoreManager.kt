package com.bangkit.bangkitcapstone.model.data.local.store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager private constructor(private val dataStore: DataStore<Preferences>) {

    private val USER_DATA = mapOf(
        "weight" to stringPreferencesKey("weight"),
        "height" to stringPreferencesKey("height"),
        "age" to stringPreferencesKey("age"),
        "bmr" to stringPreferencesKey("bmr"),
        "activity" to stringPreferencesKey("activity"),
        "cal" to stringPreferencesKey("cal"),
        "water" to stringPreferencesKey("water"),
        "token" to stringPreferencesKey("token"),
        "username" to stringPreferencesKey("email"),
        "email" to stringPreferencesKey("username"),
    )

    private val THEME_KEYS = booleanPreferencesKey("theme_settings")

    suspend fun saveMapData(data: Map<String, String>) {
        dataStore.edit { preferences ->
            data.forEach { (key, value) ->
                val dataStoreKey =
                    USER_DATA[key] ?: throw IllegalArgumentException("Invalid key")
                preferences[dataStoreKey] = value
            }
        }
    }

    fun getData(key: String): Flow<String> {
        val dataStoreKey = USER_DATA[key] ?: throw IllegalArgumentException("Invalid key")
        return dataStore.data.map { preferences ->
            preferences[dataStoreKey] ?: ""
        }
    }

    fun getAllData(): Flow<Map<String, String>> {
        return dataStore.data.map { preferences ->
            val allData = mutableMapOf<String, String>()
            USER_DATA.keys.forEach { key ->
                val dataStoreKey = USER_DATA[key] ?: throw IllegalArgumentException("Invalid key")
                allData[key] = preferences[dataStoreKey] ?: ""
            }
            allData
        }
    }

    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[THEME_KEYS] ?: false
        }
    }

    suspend fun saveThemeSetting(state: Boolean) {
        dataStore.edit { preferences ->
            preferences[THEME_KEYS] = state
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: DataStoreManager? = null

        fun getInstance(dataStore: DataStore<Preferences>): DataStoreManager {
            return INSTANCE ?: synchronized(this) {
                val instance = DataStoreManager(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

}