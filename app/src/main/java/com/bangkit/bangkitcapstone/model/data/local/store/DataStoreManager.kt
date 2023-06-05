package com.bangkit.bangkitcapstone.model.data.local.store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

class DataStoreManager private constructor(private val dataStore: DataStore<Preferences>) {


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