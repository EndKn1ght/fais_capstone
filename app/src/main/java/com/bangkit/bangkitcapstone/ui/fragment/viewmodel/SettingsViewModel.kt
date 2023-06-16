package com.bangkit.bangkitcapstone.ui.fragment.viewmodel

import androidx.lifecycle.ViewModel
import com.bangkit.bangkitcapstone.model.data.AppRepository
import com.bangkit.bangkitcapstone.model.data.local.entity.CaloriesDailyEntity

class SettingsViewModel(private val repos: AppRepository) : ViewModel() {

    fun deleteHistory() = repos.deleteAllHistory()

    fun insertIntake(list: List<CaloriesDailyEntity>) = repos.inserListDaily(list)

    fun uploadCaloriesIntake(
        token: String,
        date: String,
        desc: String,
        cal: Float
    ) = repos.uploadCaloriesIntake(token, date, desc, cal)

    fun uploadWorkoutIntake(
        token: String,
        id: String,
        date: String,
        duration: Float,
        cal: Float,
    ) = repos.uploadWorkoutIntake(token, id, date, duration, cal)

    fun downloadHistory(token: String) = repos.downloadHistory(token)

    fun getToken(key : String) = repos.getUserData(key)

    fun getAllWorkout() = repos.getAllDailyWorkout()

    fun getAllCaloriesIntake() = repos.getAllDaily()

}