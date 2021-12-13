package com.example.recipe.data.repository

import com.example.recipe.data.database.AppDatabase
import com.example.recipe.data.entity.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//Created by Khangpv on 12/12/2021.

interface AddNewRecipeRepository {
    suspend fun addOrEditRecipe(recipe: Recipe)
   suspend fun deleteRecipeBy(id: Long)
}

class AddNewRecipeRepositoryImpl(private val appDatabase: AppDatabase) : AddNewRecipeRepository {
    override suspend fun addOrEditRecipe(recipe: Recipe) =
        withContext(Dispatchers.IO) {
            appDatabase.recipeDao().addOrEditRecipe(recipe)
        }

    override suspend fun deleteRecipeBy(id: Long) =
        withContext(Dispatchers.IO) {
            appDatabase.recipeDao().deleteRecipeBy(id)


        }

}