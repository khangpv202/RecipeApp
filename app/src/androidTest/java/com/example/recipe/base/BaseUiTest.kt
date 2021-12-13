package com.example.recipe.base

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import com.example.recipe.di.AppModule
import org.junit.Rule
import org.koin.android.ext.koin.androidContext
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.mock.MockProvider
import org.koin.test.mock.MockProviderRule
//import org.koin.test.KoinTestRule
//import org.koin.test.mock.MockProviderRule
import org.mockito.Mockito
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

open class BaseUiTest<T : AppCompatActivity>(clazz: Class<T>) : KoinTest {
    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val activityRule = object : ActivityTestRule<T>(clazz, true, false) {}

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        val applicationContext = ApplicationProvider.getApplicationContext<Application>()
        androidContext(applicationContext)
        modules(AppModule.dataModel, AppModule.scopeModel)
    }

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

    fun onRecyclerViewClickAt(recyclerView: Int, position: Int) {
        val actionOnItemAtPosition =
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                position,
                ViewActions.click()
            )
        Espresso.onView(ViewMatchers.withId(recyclerView)).perform(actionOnItemAtPosition)
    }
}