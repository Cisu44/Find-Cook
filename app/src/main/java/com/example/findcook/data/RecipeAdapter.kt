package com.example.findcook.data

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.res.TypedArrayUtils.getString
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.findcook.FirebaseRepository
import com.example.findcook.R
import com.google.android.material.snackbar.Snackbar
import java.util.*
import kotlin.collections.ArrayList

class RecipeAdapter(private val listener: OnRecipeClick,
                    private val longListener: OnRecipeClick ) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    private val recipesList = ArrayList<Recipe>()
    private val repository = FirebaseRepository()

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

            view.setOnLongClickListener{
                longListener.onRecipeLongClick(recipesList[adapterPosition], adapterPosition)
                return@setOnLongClickListener true
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

        val buttonViewOption = holder.itemView.findViewById<Button>(R.id.recipe_3dots)
        buttonViewOption.setOnClickListener(object:View.OnClickListener {

            override fun onClick(v: View?) {
                val popup = PopupMenu(v?.context, buttonViewOption)
                popup.inflate(R.menu.recipe_popup_menu)

                    popup.setOnMenuItemClickListener{
                    when(it.itemId){
                        R.id.addToFavourites ->{
                            repository.addFavouriteRecipe(recipesList[position])
                            val snackText = v?.context?.resources?.getString(R.string.recipe_addToFav,recipesList[position].name)
                            Snackbar.make(v!!,snackText!!,Snackbar.LENGTH_SHORT).show()
                            return@setOnMenuItemClickListener true
                        }
                        R.id.addToForLater ->{
                            repository.addForLaterRecipe(recipesList[position])
                            val snackText = v?.context?.resources?.getString(R.string.recipe_addToForLater,recipesList[position].name)
                            Snackbar.make(v!!,snackText!!,Snackbar.LENGTH_SHORT).show()
                            return@setOnMenuItemClickListener true
                        }

                        else -> {
                            return@setOnMenuItemClickListener false
                        }
                    }
                }
                popup.show()
            }
        })
    }


    private fun bindData(holder: RecipeViewHolder) {
        val nameTV = holder.itemView.findViewById<TextView>(R.id.recipe_name_textView)
        val complexityTV = holder.itemView.findViewById<TextView>(R.id.recipe_complexity_textView)
        val categoryTV = holder.itemView.findViewById<TextView>(R.id.recipe_main_category_textView)
        val imageIV = holder.itemView.findViewById<ImageView>(R.id.recipe_image_imageView)

        nameTV.text = recipesList[holder.adapterPosition].name
        complexityTV.text = recipesList[holder.adapterPosition].complexityLevel
        categoryTV.text = recipesList[holder.adapterPosition].category
        Glide.with(holder.itemView)
            .load(recipesList[holder.adapterPosition].image)
            .into(imageIV)

    }

    override fun getItemCount(): Int {
         return recipesList.size
    }

    interface OnRecipeClick{
        fun onRecipeClick(recipe: Recipe, position: Int)
        fun onRecipeLongClick(recipe: Recipe, position: Int)
    }

   fun filter(text: String): ArrayList<Recipe> {
        val mText = text.trim().toLowerCase(Locale.ROOT)
        val filteredList: ArrayList<Recipe> = arrayListOf()
        for(item in recipesList){
            if(item.name.toLowerCase(Locale.ROOT).contains(mText)||item.description.toLowerCase(Locale.ROOT).contains(mText)){
                filteredList.add(item)
            }
            notifyDataSetChanged()
        }
       return filteredList

   }

    fun removeRecipe(recipe: Recipe, position: Int){
        recipesList.remove(recipe)
        notifyItemRemoved(position)
    }




}
