package com.example.findcook.recipeDetails

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.findcook.BaseFragment
import com.example.findcook.R
import com.example.findcook.data.Recipe
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout


class RecipeDetailsFragment(private val recipe: Recipe) : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_recipe_details, container, false)
     }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecipeData()
    }
     

    private fun setRecipeData(){
        val nameTV = view?.findViewById<TextView>(R.id.recipe_detail_name)
        val categoryTV = view?.findViewById<TextView>(R.id.recipe_detail_category)
        val ingredientsAmountTV = view?.findViewById<TextView>(R.id.recipe_details_ingredientsAmount)
        val descriptionTV = view?.findViewById<TextView>(R.id.recipe_details_description)
        val imageIV = view?.findViewById<ImageView>(R.id.recipe_details_image)
        val stepsTV = view?.findViewById<TextView>(R.id.recipe_details_steps)
        val ingredientsTV = view?.findViewById<TextView>(R.id.recipe_details_ingredients)

        nameTV?.text = recipe.name
        categoryTV?.text = recipe.category
        ingredientsAmountTV?.text = recipe.ingredientsAmount
        descriptionTV?.text = recipe.description
        for(i in  recipe.ingredients.values ) {
            ingredientsTV?.append( i.plus("\r\n"))
        }
        for(i in recipe.steps.values.sortedBy { key -> key }) {
            stepsTV?.append(i.plus("\r\n \r\n"))
        }
        Glide.with(this)
            .load(recipe.image)
            .override(1100)
            .centerCrop()
            .into(imageIV!!)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
    }

}




