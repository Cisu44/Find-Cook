package com.example.findcook.recipeDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.findcook.R
import com.example.findcook.data.Recipe


class RecipeDetailsFragment(recipe: Recipe) : Fragment() {

    val recipe = recipe
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe_details, container, false)
     }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nameTV = view.findViewById<TextView>(R.id.recipe_detail_name)
        val categoryTV = view.findViewById<TextView>(R.id.recipe_detail_category)
        val complexityTV = view.findViewById<TextView>(R.id.recipe_detail_complexity)

        nameTV.text = recipe.name
        categoryTV.text = recipe.category
        complexityTV.text = recipe.complexityLevel
    }


}