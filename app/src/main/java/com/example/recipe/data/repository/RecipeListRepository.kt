package com.example.recipe.data.repository

import com.example.recipe.data.database.AppDatabase
import com.example.recipe.data.entity.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//Created by Khangpv on 11/12/2021.

interface RecipeListRepository {
    suspend fun getAllRecipe(): List<Recipe>

    suspend fun getAllRecipeWith(type: String): List<Recipe>
}

class RecipeListRepositoryImpl(private val appDatabase: AppDatabase) : RecipeListRepository {
    override suspend fun getAllRecipe(): List<Recipe> =
        withContext(Dispatchers.IO) {
            appDatabase.recipeDao().getAllRecipe()
        }

    override suspend fun getAllRecipeWith(type: String): List<Recipe> =
        withContext(Dispatchers.IO) {
            appDatabase.recipeDao().getAllRecipeWith(type)
        }
}