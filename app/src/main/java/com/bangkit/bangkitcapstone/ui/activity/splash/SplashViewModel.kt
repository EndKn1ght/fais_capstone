package com.bangkit.bangkitcapstone.ui.activity.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bangkit.bangkitcapstone.model.data.AppRepository

class SplashViewModel(private val repos: AppRepository) : ViewModel() {

    fun getThemeSettings(): LiveData<Boolean> = repos.getThemeSettings()

    fun getToken(token: String) = repos.getUserData(token)
}