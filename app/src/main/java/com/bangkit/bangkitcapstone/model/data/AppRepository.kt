package com.bangkit.bangkitcapstone.model.data

import com.bangkit.bangkitcapstone.model.data.local.store.DataStoreManager

class AppRepository(
    private val dataStore: DataStoreManager
) {

    companion object {
        @Volatile
        private var instance: AppRepository? = null
        fun getInstance(
            dataStore: DataStoreManager
        ): AppRepository =
            instance ?: synchronized(this) {
                instance ?: AppRepository(dataStore)
            }.also { instance = it }
    }
}