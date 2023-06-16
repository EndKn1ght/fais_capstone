package com.bangkit.bangkitcapstone.ui.fragment.viewmodel

import androidx.lifecycle.ViewModel
import com.bangkit.bangkitcapstone.model.data.AppRepository

class RecipesViewModel(private val repos: AppRepository): ViewModel() {

    fun getRecipeList() = repos.getRecipesList()

}