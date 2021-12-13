package com.example.recipe.data.database

import androidx.room.*
import com.example.recipe.data.entity.Recipe

//Created by Khangpv on 11/12/2021.

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipe")
    fun getAllRecipe(): List<Recipe>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addOrEditRecipe(recipe: Recipe)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addOrEditRecipe(recipes: List<Recipe>)

    @Query("SELECT * FROM recipe where id=:recipeId")
    fun getRecipeById(recipeId: String): Recipe?

    @Query("select count(*) from recipe")
    fun numberOfRow(): Int

    @Query("SELECT * FROM recipe where category=:type")
    fun getAllRecipeWith(type: String): List<Recipe>

    @Query("DELETE FROM recipe WHERE id=:recipeId")
    fun deleteRecipeBy(recipeId: Long)
}