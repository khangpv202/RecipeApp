package com.example.recipe.userCase

import com.example.recipe.data.entity.Recipe
import com.example.recipe.data.repository.AddNewRecipeRepository
import com.example.recipe.data.repository.RecipeListRepository

//Created by Khangpv on 12/12/2021.

interface AddNewRecipeUserCase {
    suspend fun addOrEditRecipe(recipe: Recipe)
    suspend fun deleteRecipeBy(id:Long)
}

class AddNewRecipeUserCaseImpl(private val repository: AddNewRecipeRepository) :
    AddNewRecipeUserCase {
    override suspend fun addOrEditRecipe(recipe: Recipe) {
        repository.addOrEditRecipe(recipe)
    }

    override suspend fun deleteRecipeBy(id: Long) {
        repository.deleteRecipeBy(id)
    }
}