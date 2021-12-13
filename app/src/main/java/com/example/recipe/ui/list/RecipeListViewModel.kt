package com.example.recipe.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe.data.entity.Recipe
import com.example.recipe.userCase.RecipeListUserCase
import kotlinx.coroutines.launch

//Created by Khangpv on 11/12/2021.

class RecipeListViewModel(private val recipeListUserCase: RecipeListUserCase) : ViewModel() {
    val allRecipeLiveData = MutableLiveData<List<Recipe>>()
    suspend fun getAllRecipeWithTypeList(type:String) {
        viewModelScope.launch {
            allRecipeLiveData.value = recipeListUserCase.getAllRecipeWith(type)
        }
    }
}