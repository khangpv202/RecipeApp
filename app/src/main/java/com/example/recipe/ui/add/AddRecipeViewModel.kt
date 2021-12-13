package com.example.recipe.ui.add

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe.SingleLiveEvent
import com.example.recipe.data.entity.Recipe
import com.example.recipe.userCase.AddNewRecipeUserCase
import kotlinx.coroutines.launch
import org.koin.android.ext.android.getKoin
import org.koin.core.scope.Scope
import org.koin.java.KoinJavaComponent.getKoin

//Created by Khangpv on 12/12/2021.

const val TAKE_PHOTO = 0
const val SELECT_PHOTO = 1

class AddRecipeViewModel(private val addNewRecipeUserCase: AddNewRecipeUserCase) : ViewModel() {
    private val scope: Scope = getKoin().getOrCreateScope<AddRecipeScope>("ScopeAddEditRecipe")
    val photoActionLiveData = SingleLiveEvent<Int>()
    val enableSaveButtonLiveData = MutableLiveData<Boolean>()
    val saveNewRecipeLiveData = MutableLiveData<Boolean>()
    val deleteRecipeLiveData = MutableLiveData<Boolean>()

    lateinit var type: String
    private var recipeName: String? = null
    var photoUrl: String? = null
    private var ingredients: String? = null
    private var steps: String? = null
    private var recipeId: Long = 0
    fun updateRecipeId(recipeId: Long) {
        this.recipeId = recipeId
    }

    fun updateRecipeName(recipeName: String?) {
        this.recipeName = recipeName
        validate()
    }

    fun updatePhotoUrl() {
        validate()
    }

    fun updateIngredients(ingredients: String?) {
        this.ingredients = ingredients
        validate()
    }

    fun updateSteps(steps: String?) {
        this.steps = steps
        validate()
    }

    fun updateType(type: String) {
        this.type = type
    }

    private fun validate() {
        val isNotOk =
            photoUrl.isNullOrBlank() || ingredients.isNullOrBlank() || steps.isNullOrBlank()
        enableSaveButtonLiveData.value = !isNotOk
    }

    fun saveNewRecipe() {
        viewModelScope.launch {
            addNewRecipeUserCase.addOrEditRecipe(
                Recipe(
                    category = type, name = recipeName!!, imageUrl = photoUrl!!,
                    ingredients = ingredients!!, steps = steps!!
                ).apply {
                    if (this@AddRecipeViewModel.recipeId != 0L) {
                        id = this@AddRecipeViewModel.recipeId
                    }
                }
            )
            saveNewRecipeLiveData.value = true
        }
    }

    fun deleteCurrentRecipe(recipeId: Long) {
        viewModelScope.launch {
            addNewRecipeUserCase.deleteRecipeBy(recipeId)
            deleteRecipeLiveData.value = true
        }
    }

    override fun onCleared() {
        scope.close()
        super.onCleared()
    }
}