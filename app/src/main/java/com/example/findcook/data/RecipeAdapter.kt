package com.example.findcook.data

import android.provider.DocumentsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import androidx.recyclerview.widget.SortedListAdapterCallback
import com.example.findcook.FirebaseRepository
import com.example.findcook.MainMenuAcitivity
import com.example.findcook.R
import java.util.*
import kotlin.collections.ArrayList

class RecipeAdapter(private val listener: OnRecipeClick) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

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
                            return@setOnMenuItemClickListener true
                        }
                        R.id.addToForLater ->{
                            repository.addForLaterRecipe(recipesList[position])
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




}
