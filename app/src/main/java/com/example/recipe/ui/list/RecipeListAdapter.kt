package com.example.recipe.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.recipe.R
import com.example.recipe.data.entity.Recipe

//Created by Khangpv on 12/12/2021.

class RecipeListAdapter(private val recipeList: List<Recipe>) :
    RecyclerView.Adapter<RecipeListAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvRecipeName: TextView = view.findViewById(R.id.tvRecipeName)
        fun bindView(recipe: Recipe) {
            tvRecipeName.text = recipe.name
            tvRecipeName.setOnClickListener {
                it.findNavController().navigate(
                    R.id.action_recipeListFragment_to_addRecipeFragment,
                    bundleOf("recipe" to recipe)
                )
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recipe_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(recipeList[position])

    }

    override fun getItemCount(): Int = recipeList.size
}