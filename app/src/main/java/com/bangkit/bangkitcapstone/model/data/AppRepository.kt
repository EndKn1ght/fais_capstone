package com.bangkit.bangkitcapstone.model.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.bangkit.bangkitcapstone.model.data.local.store.DataStoreManager

class AppRepository(
    private val dataStore: DataStoreManager
) {

    fun saveUserData(data: Map<String, String>): LiveData<UiState<Boolean>> = liveData {
        emit(UiState.Loading)
        try {
            dataStore.saveMapData(data)
            emit(UiState.Success(true))
        } catch (e: Exception) {
            emit(UiState.Error(e.message.toString()))
        }
    }

    fun getUserData(key: String): LiveData<String> {
        return dataStore.getData(key).asLiveData()
    }

    fun getAllUserData() = dataStore.getAllData()

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