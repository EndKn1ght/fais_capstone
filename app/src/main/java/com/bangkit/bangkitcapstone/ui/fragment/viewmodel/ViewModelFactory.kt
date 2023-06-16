package com.bangkit.bangkitcapstone.ui.fragment.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.bangkitcapstone.di.Injection
import com.bangkit.bangkitcapstone.model.data.AppRepository
import com.bangkit.bangkitcapstone.ui.activity.splash.SplashViewModel

class ViewModelFactory private constructor(private val repos: AppRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            CalculatorViewModel::class.java -> CalculatorViewModel(repos) as T
            ProfileViewModel::class.java -> ProfileViewModel(repos) as T
            SplashViewModel::class.java -> SplashViewModel(repos) as T
            WorkoutViewModel::class.java -> WorkoutViewModel(repos) as T
            AuthViewModel::class.java -> AuthViewModel(repos) as T
            SettingsViewModel::class.java -> SettingsViewModel(repos) as T
            RecipesViewModel::class.java -> RecipesViewModel(repos) as T
            FoodDetailViewModel::class.java -> FoodDetailViewModel(repos) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}