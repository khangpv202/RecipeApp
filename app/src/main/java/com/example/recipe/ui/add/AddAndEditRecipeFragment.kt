package com.example.recipe.ui.add

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.recipe.R
import com.example.recipe.data.entity.Recipe
import kotlinx.android.synthetic.main.fragment_add_recipe.*
import org.koin.android.ext.android.getKoin
import org.koin.core.scope.Scope
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class AddAndEditRecipeFragment : Fragment(R.layout.fragment_add_recipe) {
    private val scope: Scope = getKoin().getOrCreateScope<AddRecipeScope>("ScopeAddEditRecipe")
    private val viewModel = scope.inject<AddRecipeViewModel>().value

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recipe = requireNotNull(arguments?.getParcelable<Recipe>("recipe"))
        viewModel.updateType(requireNotNull(recipe.category))
        if (recipe.id != 0L) {
            viewForEditing(recipe)
        }else{
            Glide.with(this).load(viewModel.photoUrl).into(ivRecipe)
        }
        observe()
        handleOnclickListener(recipe)
        handleTextChangedListener()
    }

    private fun viewForEditing(recipe: Recipe) {
        Glide.with(this).load(recipe.imageUrl).into(ivRecipe)
        etRecipeName.setText(recipe.name)
        etIngredients.setText(recipe.ingredients)
        etSteps.setText(recipe.steps)
        viewModel.photoUrl = recipe.imageUrl
        viewModel.updateIngredients(recipe.ingredients)
        viewModel.updateSteps(recipe.steps)
        viewModel.updateRecipeName(recipe.name)
        viewModel.updateRecipeId(recipe.id)
        btDeleteRecipe.visibility = View.VISIBLE
        addEditToolBar.title = getString(R.string.edit_recipe)
    }

    private fun handleTextChangedListener() {
        etRecipeName.addTextChangedListener {
            viewModel.updateRecipeName(it.toString())
        }
        etIngredients.addTextChangedListener {
            viewModel.updateIngredients(it.toString())
        }
        etSteps.addTextChangedListener {
            viewModel.updateSteps(it.toString())
        }
    }

    private fun handleOnclickListener(recipe: Recipe) {
        ivTakePhoto.setOnClickListener {
            findNavController().navigate(R.id.action_addRecipeFragment_to_photoDialog)
        }

        btSaveRecipe.setOnClickListener {
            viewModel.saveNewRecipe()
        }
        btDeleteRecipe.setOnClickListener {
            viewModel.deleteCurrentRecipe(recipe.id)
        }
    }

    private fun observe() {
        viewModel.photoActionLiveData.observe(viewLifecycleOwner, {
            when (it) {
                TAKE_PHOTO -> {
                    val takePhotoIntent =
                        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                            // Ensure that there's a camera activity to handle the intent
                            takePictureIntent.resolveActivity(requireActivity().packageManager)
                                ?.also {
                                    // Create the File where the photo should go
                                    val photoFile: File? = try {
                                        createImageFile()
                                    } catch (ex: IOException) {
                                        // Error occurred while creating the File
                                        null
                                    }
                                    // Continue only if the File was successfully created
                                    photoFile?.also {
                                        val photoURI: Uri = FileProvider.getUriForFile(
                                            requireActivity(),
                                            "com.example.android.fileprovider",
                                            it
                                        )
                                        takePictureIntent.putExtra(
                                            MediaStore.EXTRA_OUTPUT,
                                            photoURI
                                        )
                                    }
                                }
                        }
                    takePhotoLauncher.launch(takePhotoIntent)
                }
                else -> {
                    val selectPhotoIntent = Intent()
                    selectPhotoIntent.type = "image/*"
                    selectPhotoIntent.action = Intent.ACTION_GET_CONTENT
                    selectPhotoLauncher.launch(selectPhotoIntent)
                }
            }
        })
        viewModel.enableSaveButtonLiveData.observe(viewLifecycleOwner, {
            btSaveRecipe.isEnabled = it
        })
        viewModel.saveNewRecipeLiveData.observe(viewLifecycleOwner, {
            findNavController().popBackStack()
        })
        viewModel.deleteRecipeLiveData.observe(viewLifecycleOwner, {
            findNavController().popBackStack()
        })
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            viewModel.photoUrl = absolutePath
        }
    }

    private val takePhotoLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                Glide.with(this).load(viewModel.photoUrl).into(ivRecipe)
            } else viewModel.photoUrl = null
            viewModel.updatePhotoUrl()
        }

    private val selectPhotoLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                //todo: handle the same as take picture
                println()
            }
        }
}