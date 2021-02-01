package com.example.findcook.forLater

import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.findcook.FirebaseRepository
import com.example.findcook.data.Recipe

class ForLaterViewModel : ViewModel() {
    private val repository = FirebaseRepository()
    private val user = repository.getUserData()

    val forLaterRecipes = user.switchMap {
        repository.getForLaterRecipes(it.forLaterRecipes)
    }

    fun removeRecipe(recipe: Recipe){
        repository.removeForLaterRecipe(recipe)
    }



}