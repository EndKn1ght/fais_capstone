package com.bangkit.bangkitcapstone.ui.fragment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bangkit.bangkitcapstone.model.data.AppRepository

class ProfileViewModel(private val repos: AppRepository) : ViewModel() {

    fun getThemeSettings(): LiveData<Boolean> = repos.getThemeSettings()

    fun setTheme(state: Boolean) = repos.saveThemeSetting(state)

    fun deleteToken(token: Map<String, String>) = repos.saveUserData(token)

    fun getUserData(key: String) = repos.getUserData(key)
}