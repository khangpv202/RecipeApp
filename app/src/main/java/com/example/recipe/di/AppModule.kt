package com.example.recipe.di

import android.content.Context
import androidx.room.Room
import com.example.recipe.data.database.AppDatabase
import com.example.recipe.data.repository.AddNewRecipeRepository
import com.example.recipe.data.repository.AddNewRecipeRepositoryImpl
import com.example.recipe.data.repository.RecipeListRepository
import com.example.recipe.data.repository.RecipeListRepositoryImpl
import com.example.recipe.ui.add.AddRecipeScope
import com.example.recipe.ui.add.AddRecipeViewModel
import com.example.recipe.ui.list.*
import com.example.recipe.userCase.AddNewRecipeUserCase
import com.example.recipe.userCase.AddNewRecipeUserCaseImpl
import com.example.recipe.userCase.RecipeListUserCase
import com.example.recipe.userCase.RecipeListUserCaseImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

//Created by Khangpv on 11/12/2021.

object AppModule {
    val dataModel = module {
        single<AppDatabase> { provideDataBase(get()) }
        single<RecipeListUserCase> { RecipeListUserCaseImpl(get()) }
        single<RecipeListRepository> { RecipeListRepositoryImpl(get()) }

    }
    val scopeModel = module {
        viewModel { RecipeListViewModel(get()) }
        scope<AddRecipeScope> {
            factory<AddNewRecipeUserCase> { AddNewRecipeUserCaseImpl(get()) }
            factory<AddNewRecipeRepository> { AddNewRecipeRepositoryImpl(get()) }
            viewModel { AddRecipeViewModel(get()) }
        }
    }

    private fun provideDataBase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "recipe-database"
        ).build()
    }
}