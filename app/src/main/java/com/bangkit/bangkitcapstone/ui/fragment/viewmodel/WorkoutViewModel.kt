package com.bangkit.bangkitcapstone.ui.fragment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bangkit.bangkitcapstone.model.data.AppRepository
import com.bangkit.bangkitcapstone.model.data.UiState
import com.bangkit.bangkitcapstone.model.data.local.IntakeType
import com.bangkit.bangkitcapstone.model.data.local.entity.WorkoutEntity

class WorkoutViewModel(private val repos: AppRepository) : ViewModel() {

    fun getListWorkout(): LiveData<UiState<List<WorkoutEntity>>> {
        return repos.getAllWorkoutList()
    }

    fun getBmr() = repos.getUserData("bmr")

    fun insertWorkout(name: String, intake: String, duration: String): LiveData<UiState<Float>> {
        return repos.insertIntake(name, intake, IntakeType.WORKOUT, duration)
    }

}