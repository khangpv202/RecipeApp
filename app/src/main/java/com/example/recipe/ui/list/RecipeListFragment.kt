package com.example.recipe.ui.list

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.recipe.MainActivity
import com.example.recipe.R
import com.example.recipe.data.entity.Recipe
import kotlinx.android.synthetic.main.fragment_recipe_list.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class RecipeListFragment : Fragment(R.layout.fragment_recipe_list) {
    private val viewModel: RecipeListViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? MainActivity)?.setSupportActionBar(toolBar)
        setHasOptionsMenu(true)

        handleSpinner()
        observe()
    }

    private fun observe() {
        viewModel.allRecipeLiveData.observe(viewLifecycleOwner, {
            val recipeListAdapter = RecipeListAdapter(it)
            rvRecipe.adapter = recipeListAdapter
        })
    }

    private fun handleSpinner() {
        spinnerType.adapter = ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.recipeType,
            android.R.layout.simple_spinner_dropdown_item
        )
        spinnerType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                p1: View?,
                position: Int,
                id: Long
            ) {
                val type = parent?.getItemAtPosition(position).toString()
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.getAllRecipeWithTypeList(type)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.recipe_list_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add -> {
                findNavController().navigate(
                    R.id.action_recipeListFragment_to_addRecipeFragment,
                    bundleOf("recipe" to Recipe(category = spinnerType.selectedItem.toString()))
                )
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}