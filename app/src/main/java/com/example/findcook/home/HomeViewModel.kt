package com.example.findcook.home

import androidx.lifecycle.ViewModel
import com.example.findcook.FirebaseRepository

class HomeViewModel : ViewModel() {
    private val repository = FirebaseRepository()

    val recipes = repository.getRecipesHome()
}