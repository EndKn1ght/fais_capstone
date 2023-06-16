package com.bangkit.bangkitcapstone.ui.fragment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.bangkitcapstone.model.data.AppRepository
import com.bangkit.bangkitcapstone.model.data.UiState
import com.bangkit.bangkitcapstone.model.data.local.IntakeType

class FoodDetailViewModel(private val repos: AppRepository) : ViewModel() {

    private val _nutrientData = MutableLiveData<String>()
    val nutrientData: LiveData<String> = _nutrientData

    fun setNutrien(data: String) {
        _nutrientData.value = data
    }

    fun getNutrien(): LiveData<String> {
        return nutrientData
    }

    fun insertFood(
        name: String,
        intake: String,
    ) :LiveData<UiState<Float>> {
        return repos.insertIntake(name, intake, IntakeType.FOOD)
    }
}