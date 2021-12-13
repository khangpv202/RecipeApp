package com.example.recipe

import com.example.recipe.data.entity.Recipe
import com.example.recipe.data.repository.RecipeListRepository
import com.example.recipe.userCase.RecipeListUserCase
import com.example.recipe.userCase.RecipeListUserCaseImpl
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

//Created by Khangpv on 12/12/2021.

class RecipeListUserCaseTest {
    @Mock
    lateinit var recipeListRepository: RecipeListRepository
    lateinit var recipeListUserCase: RecipeListUserCase

    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
        recipeListUserCase = RecipeListUserCaseImpl(recipeListRepository)
    }

    @Test
    fun testGetALlRecipeWithType_Seafood() {
        val type = "Seafood"
        val fakeData = FakeInitData.initData(type)
        runBlocking {
            Mockito.`when`(recipeListRepository.getAllRecipeWith(type))
                .thenReturn(fakeData)

            val result = recipeListUserCase.getAllRecipeWith(type)
            Assert.assertEquals(fakeData.size, result.size)
            val dataAtFirst = fakeData[0]
            val resultAtFirst = result[0]
            Assert.assertEquals(dataAtFirst, resultAtFirst)
        }
    }

    @Test
    fun testGetALlRecipeWithType_Seafood_withEmptyList() {
        val type = "Seafood"
        val fakeData = listOf<Recipe>()
        runBlocking {
            Mockito.`when`(recipeListRepository.getAllRecipeWith(type))
                .thenReturn(fakeData)

            val result = recipeListUserCase.getAllRecipeWith(type)
            Assert.assertEquals(fakeData.size, result.size)
            Assert.assertTrue(result.isEmpty())
        }
    }
}