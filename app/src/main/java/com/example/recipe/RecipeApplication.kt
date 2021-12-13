package com.example.recipe

import android.app.Application
import com.example.recipe.data.database.AppDatabase
import com.example.recipe.di.AppModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent

//Created by Khangpv on 12/12/2021.

class RecipeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@RecipeApplication)
            modules(listOf(AppModule.dataModel, AppModule.scopeModel))
        }

        val appDatabase = KoinJavaComponent.get<AppDatabase>(AppDatabase::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            if (appDatabase.recipeDao().numberOfRow() == 0) {
                appDatabase.recipeDao()
                    .addOrEditRecipe(FakeInitData.fakeData(this@RecipeApplication))
            }
        }
    }
}