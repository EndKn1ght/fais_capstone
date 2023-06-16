package com.bangkit.bangkitcapstone.ui.fragment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkit.bangkitcapstone.model.data.AppRepository
import com.bangkit.bangkitcapstone.model.data.UiState
import com.bangkit.bangkitcapstone.model.data.local.IntakeType
import com.bangkit.bangkitcapstone.model.data.local.entity.CaloriesDailyEntity
import com.bangkit.bangkitcapstone.model.data.local.entity.WorkoutEntity

class CalculatorViewModel(private val repos: AppRepository) : ViewModel() {

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

    fun waterCalculator(
        weight: Int
    ): Float {
        return ((weight.toFloat() * 2.20462f) * 2 / 3) * 0.0295735f
    }

    fun caloriesIntake(
        name: String,
        carbs: Float,
        prot: Float,
        fat: Float,
    ): LiveData<UiState<Float>> {
        val intake = (carbs * 4) + (prot * 4) + (fat * 9)
        return repos.insertIntake(name, intake.toString(), IntakeType.FOOD)
    }

    fun wantersIntake(intake: String): LiveData<UiState<Float>> {
        return repos.insertIntake("Water", intake, IntakeType.WATER)
    }

    fun workoutIntake(
        name: String,
        bmr: Float,
        met: Float,
        time: Float
    ): LiveData<UiState<Float>> {
        val intake = (bmr / 24) * time * met
        return repos.insertIntake(name, intake.toString(), IntakeType.WORKOUT, duration = (time * 60.0).toString())
    }

    fun getIntakeData(intakeType: IntakeType, date: String): LiveData<UiState<List<String>>> =
        repos.getIntakeData(intakeType, date)

    fun getAllDaily(): LiveData<UiState<List<CaloriesDailyEntity>>> = repos.getAllDaily()

    fun getAllDailyWorkout(): LiveData<UiState<List<CaloriesDailyEntity>>> = repos.getAllDailyWorkout()

    fun getAllWorkout(): LiveData<UiState<List<WorkoutEntity>>> = repos.getAllWorkout()

    fun getUserData(key: String): LiveData<String> = repos.getUserData(key)

    fun saveUserData(data: Map<String, String>) = repos.saveUserData(data)

    fun getAllUserData() = repos.getAllUserData().asLiveData()
}