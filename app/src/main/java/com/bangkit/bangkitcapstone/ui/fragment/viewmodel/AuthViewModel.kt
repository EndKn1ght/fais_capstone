package com.bangkit.bangkitcapstone.ui.fragment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bangkit.bangkitcapstone.model.data.AppRepository
import com.bangkit.bangkitcapstone.model.data.UiState
import com.bangkit.bangkitcapstone.model.data.remote.response.LoginResponse

class AuthViewModel(private val repos: AppRepository) : ViewModel() {

    fun loginAuth(
        username: String,
        password: String
    ): LiveData<UiState<LoginResponse>> {
        return repos.loginAuth(username, password)
    }

    fun registerAuth(
        username: String,
        password: String,
        email: String,
        date: String,
        height: Int,
        weight: Int
    ) = repos.registerAuth(username, password, email, date, height, weight)

    fun saveUserData(data: Map<String, String>) = repos.saveUserData(data)

    fun getUserDetail(token: String) = repos.getUserDetail(token)
}