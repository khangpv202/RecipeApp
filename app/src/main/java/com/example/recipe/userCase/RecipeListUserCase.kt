package com.example.recipe.userCase

import com.example.recipe.data.entity.Recipe
import com.example.recipe.data.repository.RecipeListRepository

//Created by Khangpv on 11/12/2021.

interface RecipeListUserCase {
    suspend fun getAllRecipeWith(type:String): List<Recipe>

}

class RecipeListUserCaseImpl(private val repository: RecipeListRepository) :
    RecipeListUserCase {

    override suspend fun getAllRecipeWith(type: String): List<Recipe> {
        return repository.getAllRecipeWith(type)
    }
}