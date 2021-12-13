package com.example.recipe

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.recipe.base.BaseUiTest
import com.example.recipe.base.RecyclerViewItemCountAssertion
import com.example.recipe.userCase.RecipeListUserCase
import kotlinx.android.synthetic.main.fragment_recipe_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.mock.declareMock
import org.mockito.BDDMockito

//Created by Khangpv on 12/12/2021.

@RunWith(AndroidJUnit4::class)
class RecipeListFragmentTest : BaseUiTest<MainActivity>(MainActivity::class.java) {

    private fun startActivity() {
        val intent = Intent()
        activityRule.launchActivity(intent)
    }

    private val type = "Seafood"
    private val fakeData = FakeInitData.initData(type = "Seafood")
    private fun mockDataAndStartActivity() {
        var index = 1L
        fakeData.forEach {
           it.id = index
            index++
        }
        declareMock<RecipeListUserCase> {
            runBlocking(Dispatchers.Main) {
                BDDMockito.given(this@declareMock.getAllRecipeWith(type)).willReturn(fakeData)
            }
        }
        startActivity()
    }

    @Test
    fun testRecipeListWithFourItems() {
        mockDataAndStartActivity()
        onView(withId(R.id.rvRecipe))
            .check(ViewAssertions.matches((ViewMatchers.isDisplayed())))
        fakeData.forEach {
            onView(withText(Matchers.`is`(it.name)))
                .check(ViewAssertions.matches((ViewMatchers.isDisplayed())))
        }
        onView(withId(R.id.rvRecipe)).check(RecyclerViewItemCountAssertion(4))
    }

    @Test
    fun testClickFirstRecipeInList() {
        mockDataAndStartActivity()
        onRecyclerViewClickAt(R.id.rvRecipe, 0)
        onView(withId(R.id.etRecipeName))
            .check(ViewAssertions.matches(withText(fakeData[0].name)))
        onView(withId(R.id.etIngredients))
            .check(ViewAssertions.matches(withText(fakeData[0].ingredients)))
        onView(withId(R.id.etSteps))
            .check(ViewAssertions.matches(withText(fakeData[0].steps)))
    }

}