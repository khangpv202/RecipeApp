package com.example.recipe.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.recipe.data.entity.Recipe

//Created by Khangpv on 11/12/2021.

@Database(entities = [Recipe::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}