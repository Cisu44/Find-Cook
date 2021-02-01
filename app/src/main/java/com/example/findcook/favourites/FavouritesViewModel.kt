package com.example.findcook.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.findcook.FirebaseRepository
import com.example.findcook.data.Recipe

class FavouritesViewModel : ViewModel() {
    private val repository = FirebaseRepository()

    private val user = repository.getUserData()

    val favRecipes = user.switchMap {
        repository.getFavRecipes(it.favouriteRecipes)
    }

    fun removeRecipe(recipe: Recipe){
        repository.removeFavRecipe(recipe)
    }
}