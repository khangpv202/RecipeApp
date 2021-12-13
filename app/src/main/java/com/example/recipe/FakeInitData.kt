package com.example.recipe

import android.content.Context
import com.example.recipe.data.entity.Recipe

//Created by Khangpv on 12/12/2021.

object FakeInitData {
    fun initData(type: String): List<Recipe> {
        return listOf(
            Recipe(
                name = "$type---Baked salmon with fennel & tomatoes",
                category = type,
                imageUrl = "https://www.themealdb.com/images/media/meals/1548772327.jpg",
                ingredients = "milk, chicken, beef...",
                steps = "1. 2. 3. 4 "
            ),
            Recipe(
                name = "$type---Cajun spiced fish tacos",
                category = type,
                imageUrl = "https://www.themealdb.com/images/media/meals/uvuyxu1503067369.jpg",
                ingredients = "milk, chicken, beef...",
                steps = "1. 2. 3. 4 "
            ),
            Recipe(
                name = "$type---Escovitch Fish",
                category = type,
                imageUrl = "https://www.themealdb.com/images/media/meals/1520084413.jpg",
                ingredients = "milk, chicken, beef...",
                steps = "1. 2. 3. 4 "
            ),
            Recipe(
                name = "$type---Fish Stew with Rouille",
                category = type,
                imageUrl = "https://www.themealdb.com/images/media/meals/vptqpw1511798500.jpg",
                ingredients = "milk, chicken, beef...",
                steps = "1. 2. 3. 4 "
            )
        )
    }

    fun fakeData(context: Context): List<Recipe> {
        val result = mutableListOf<Recipe>()
        context.resources.getStringArray(R.array.recipeType).forEach {
            result.addAll(initData(it))
        }
        return result
    }
}