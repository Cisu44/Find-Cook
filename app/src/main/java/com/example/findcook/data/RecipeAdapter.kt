package com.example.findcook.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.findcook.R

class RecipeAdapter(private val listener: OnRecipeClick) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    private val recipesList = ArrayList<Recipe>()

    fun setRecipes(list: List<Recipe>){
        recipesList.clear()
        recipesList.addAll(list)
        notifyDataSetChanged()
    }

    inner class RecipeViewHolder(view: View) : RecyclerView.ViewHolder(view){
        init{
            view.setOnClickListener{
                listener.onRecipeClick(recipesList[adapterPosition],adapterPosition)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recipe_single_row,parent,false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {

        bindData(holder)
    }


    private fun bindData(holder: RecipeViewHolder) {
        val nameTV = holder.itemView.findViewById<TextView>(R.id.recipe_name_textView)
        val complexityTV = holder.itemView.findViewById<TextView>(R.id.recipe_complexity_textView)
        val categoryTV = holder.itemView.findViewById<TextView>(R.id.recipe_main_category_textView)

        nameTV.text = recipesList[holder.adapterPosition].name
        complexityTV.text = recipesList[holder.adapterPosition].complexityLevel
        categoryTV.text = recipesList[holder.adapterPosition].category
    }

    override fun getItemCount(): Int {
        return recipesList.size
    }

    interface OnRecipeClick{
        fun onRecipeClick(recipe: Recipe, position: Int)
    }
}