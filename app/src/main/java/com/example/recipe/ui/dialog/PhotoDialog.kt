package com.example.recipe.ui.dialog

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.fragment.app.DialogFragment
import com.example.recipe.R
import com.example.recipe.ui.add.AddRecipeScope
import com.example.recipe.ui.add.AddRecipeViewModel
import com.example.recipe.ui.add.SELECT_PHOTO
import com.example.recipe.ui.add.TAKE_PHOTO
import kotlinx.android.synthetic.main.photo_dialog.*
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope

//Created by Khangpv on 12/12/2021.

class PhotoDialog : DialogFragment(R.layout.photo_dialog) {
    private val scope: Scope = getKoin().getOrCreateScope<AddRecipeScope>("ScopeAddEditRecipe")
    private val viewModel = scope.inject<AddRecipeViewModel>().value
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvTakePhoto.setOnClickListener {
            viewModel.photoActionLiveData.value = TAKE_PHOTO
            dismiss()
        }

        tvSelectPhoto.setOnClickListener {
            viewModel.photoActionLiveData.value = SELECT_PHOTO
            dismiss()
        }

    }
}