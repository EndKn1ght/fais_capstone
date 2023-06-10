package com.bangkit.bangkitcapstone.ui.fragment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkit.bangkitcapstone.model.data.AppRepository

class CaloriesViewModel(private val repos: AppRepository) : ViewModel() {

    fun bmrCalculator(
        weight: Int,
        height: Int,
        age: Int,
        gender: Boolean,
        activity: String
    ): Float {
        val bmr: Float = if (gender) {
            10 * weight + 6.25f * height - 5 * age + 5
        } else {
            10 * weight + 6.25f * height - 5 * age - 161
        }
        val bmrWithActivity = when (activity) {
            "Sedentary" -> bmr * 1.2f
            "Lightly Active" -> bmr * 1.375f
            "Moderately Active" -> bmr * 1.55f
            "Very Active" -> bmr * 1.725f
            "Extra Active" -> bmr * 1.9f
            else -> bmr
        }
        return bmrWithActivity
    }

    fun getUserData(key: String): LiveData<String> = repos.getUserData(key)

    fun saveUserData(data: Map<String, String>) = repos.saveUserData(data)

    fun getAllUserData() = repos.getAllUserData().asLiveData()
}